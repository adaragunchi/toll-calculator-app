package com.tollcalculator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.tollcalculator.enums.TollFreeVehicles;
import com.tollcalculator.pojo.FeeByTimeInterval;
import com.tollcalculator.pojo.Vehicle;
import com.tollcalculator.service.TollService;
import com.tollcalculator.util.LoadDataFromYmlFile;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static com.tollcalculator.constants.TollCalculatorConstants.*;

/**
 * This class responsible to check toll free day,tollFreeVehicle
 *  and also calculate the toll fee based on the time interval
 */
public class TollServiceImpl implements TollService {

    /**
     *  Check weather input date is TollFree day
     * @param day
     * @return
     */
    @Override
    public boolean isTollFreeDay(LocalDate day) {
        if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return true;
        } else {
            return LoadDataFromYmlFile.holidayMap.containsKey(day);
        }
    }

    /**
     * Calculate the toll fee based on the provided time interval
     * @param time
     * @return
     */
    @Override
    public double getTollFee(LocalTime time) {
        return LoadDataFromYmlFile.timeFeeList.stream()
                .filter(timeFee -> isMatched(timeFee, time))
                .findAny()
                .map(FeeByTimeInterval::getFee)
                .orElse(0d);
    }

    /**
     * Validate input vehicle as well as incoming dates
     * @param vehicle
     * @param dates
     * @return
     */
    @Override
    public boolean isValid(Vehicle vehicle, LocalDateTime... dates) {
        Optional.ofNullable(vehicle).orElseThrow(()->new RuntimeException(NULL_PARAM_VEHICLE_MSG));

        if (isTollFreeVehicle(vehicle)) {
            return false;
        }

        if (ObjectUtil.isNull(dates) || CollUtil.isEmpty(Arrays.asList(dates))) {
            throw new RuntimeException(NULL_PARAM_DATES_MSG);
        }
        if (Arrays.stream(dates)
                .map(LocalDateTime::toLocalDate)
                .anyMatch(date -> !date.equals(dates[0].toLocalDate()))) {
            throw new RuntimeException(MORE_THAN_ONE_DAY_MSG);
        }
        if (isTollFreeDay(dates[0].toLocalDate())) {
            return false;
        }
        return true;
    }


    private boolean isTollFreeVehicle(Vehicle vehicle) {
        if (ObjectUtil.isNull(vehicle))
            return false;
        String vehicleType = vehicle.getType();
        return vehicleType.equals(TollFreeVehicles.MOTORBIKE.getType())
                || vehicleType.equals(TollFreeVehicles.TRACTOR.getType())
                || vehicleType.equals(TollFreeVehicles.EMERGENCY.getType())
                || vehicleType.equals(TollFreeVehicles.DIPLOMAT.getType())
                || vehicleType.equals(TollFreeVehicles.FOREIGN.getType())
                || vehicleType.equals(TollFreeVehicles.MILITARY.getType());
    }
    /**
     * Determine whether the given time is between StartTime and EndTime of timeFee
     */
    private boolean isMatched(FeeByTimeInterval timeFee, LocalTime time) {
        return withinStartTime(timeFee, time) && withinEndTime(timeFee, time);
    }

    private boolean withinStartTime(FeeByTimeInterval timeFee, LocalTime time) {
        return (timeFee.getStart().equals(time) || timeFee.getStart().isBefore(time));
    }

    private boolean withinEndTime(FeeByTimeInterval timeFee, LocalTime time) {
        return (timeFee.getEnd().equals(time) || timeFee.getEnd().isAfter(time));
    }
}
