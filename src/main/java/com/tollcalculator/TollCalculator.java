package com.tollcalculator;

import com.tollcalculator.pojo.Vehicle;
import com.tollcalculator.service.TollService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.tollcalculator.constants.TollCalculatorConstants.LIMIT_MINUTES_RECHARGE;
import static com.tollcalculator.constants.TollCalculatorConstants.MAX_FEE_FOR_ONE_DAY;

/**
 * @author :Manjunath Adaragunchi
 */
public class TollCalculator {
    private TollService tollService;

    public TollCalculator(TollService tollService) {
        this.tollService = tollService;
    }

    /**
     *
     * @param vehicle
     * @param dates
     * @return
     */
    public double getTollFee(Vehicle vehicle, LocalDateTime... dates) {

      //Validate incoming vehicle as well as dates
      if (!tollService.isValid(vehicle,dates)){
          return 0;
      }

      //Filter out the valid entries which will return more than 0 toll fee
        List<LocalTime> validTimeList = Arrays.stream(dates)
                .map(LocalDateTime::toLocalTime)
                .filter(time -> tollService.getTollFee(time) > 0)
                .sorted()
                .collect(Collectors.toList());

        if (validTimeList.isEmpty()) {
            return 0;
    }

        LocalTime intervalStartTime=validTimeList.get(0);
        double totalFee = 0;
        double tempFee = tollService.getTollFee(intervalStartTime);

        for (LocalTime nextTime : validTimeList) {

            /**
             * The maximum fee for one day is 60 SEK
             */
            if (totalFee >= MAX_FEE_FOR_ONE_DAY) {
                return MAX_FEE_FOR_ONE_DAY;
            }
            double nextFee = tollService.getTollFee(nextTime);
            if (intervalStartTime.plusMinutes(LIMIT_MINUTES_RECHARGE).isAfter(nextTime)) {
                if (totalFee > 0) {
                    totalFee -= tempFee;
                }
                if (nextFee >= tempFee) {
                    tempFee = nextFee;
                }
                totalFee += tempFee;
            } else {
                totalFee += nextFee;
                intervalStartTime = nextTime;
                tempFee = nextFee;
            }
        }
        return totalFee;
    }
}
