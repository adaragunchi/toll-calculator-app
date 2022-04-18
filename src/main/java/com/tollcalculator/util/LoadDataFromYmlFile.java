package com.tollcalculator.util;

import com.tollcalculator.pojo.FeeByTimeInterval;
import com.tollcalculator.pojo.FeeByTimeIntervalList;
import com.tollcalculator.pojo.TollFreeMonthDateList;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tollcalculator.constants.TollCalculatorConstants.FEE_BY_TIME_LIST_YAML_FILE;
import static com.tollcalculator.constants.TollCalculatorConstants.HOLIDAY_MONTH_DATE_LIST_YAML_FILE;

/**
 * This class used to load fees and holidays list from the yaml file
 */
public class LoadDataFromYmlFile {

    public static final List<FeeByTimeInterval> timeFeeList = new ArrayList<>();
    public static final Map<LocalDate, Boolean> holidayMap = new HashMap<>();

    static {
        loadHolidayDatesFromYaml();
        loadTollFeeByTimeFromYaml();
    }

    /**
     * This method loads the toll fee based on the time intervals from the yaml file
     */
    public static void loadTollFeeByTimeFromYaml() {
        try (InputStream in = LoadDataFromYmlFile.class.getResourceAsStream(FEE_BY_TIME_LIST_YAML_FILE)) {
            Yaml yaml = new Yaml(new Constructor(FeeByTimeIntervalList.class));
            FeeByTimeIntervalList list = yaml.load(in);

            list.getFeeByTimeIntervalList()
                    .stream()
                    .map(timeFeeObj -> timeFeeToDto(timeFeeObj))
                    .forEach(timeFeeList::add);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private static FeeByTimeInterval timeFeeToDto(FeeByTimeIntervalList.TimeFeeObj timeFeeObj) {
        return new FeeByTimeInterval()
                .setStart(LocalTime.parse(timeFeeObj.getStartTime()))
                .setEnd(LocalTime.parse(timeFeeObj.getEndTime()))
                .setFee(timeFeeObj.getFee());
    }

    /**
     * This method used to retrive the holidays list from the yaml file
     */
    public static void loadHolidayDatesFromYaml() {
        try (InputStream in = LoadDataFromYmlFile.class.getResourceAsStream(HOLIDAY_MONTH_DATE_LIST_YAML_FILE)) {

            Constructor constructor = new Constructor(TollFreeMonthDateList.class);
            TypeDescription configDesc = new TypeDescription(TollFreeMonthDateList.class);
            configDesc.putListPropertyType("holidayMonthDatesList", TollFreeMonthDateList.HolidayMonthDateObj.class);
            constructor.addTypeDescription(configDesc);
            Yaml yaml = new Yaml(constructor);
            TollFreeMonthDateList list = yaml.load(in);

            list.getHolidayMonthDatesList()
                    .stream()
                    .forEach(holidayMonthDateObj -> {
                           int year = LocalDate.now().getYear();
                           holidayMonthDateObj.getDates().stream().forEach(holidayMonthDatesObj->{
                           holidayMap.put(LocalDate.of(year, holidayMonthDateObj.getMonth(), holidayMonthDatesObj), true);
                       });
                    });
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
