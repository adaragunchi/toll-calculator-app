package com.tollcalculator.constants;

/**
 * @author : Manjunath.S.Adaragunchi
 */
public class TollCalculatorConstants {
    public static final String MOTORBIKE="Motorbike";
    public static final String DIPLOMAT="Diplomat";
    public static final String EMERGENCY="Emergency";
    public static final String MILITARY="Military";
    public static final String TRACTOR="Tractor";
    public static final String FOREIGN="Foreign";
    public static final String CAR="Car";
    public static final int MAX_FEE_FOR_ONE_DAY = 60;//SEK
    public static final int LIMIT_MINUTES_RECHARGE = 60;//MINUTES
    public static final String FEE_BY_TIME_LIST_YAML_FILE = "/feeByTimeIntervalList.yaml";
    public static final String HOLIDAY_MONTH_DATE_LIST_YAML_FILE = "/tollFreeMonthDate.yaml";
    public static final String NULL_PARAM_DATES_MSG ="DATE CAN'T BE NULL" ;
    public static final String NULL_PARAM_VEHICLE_MSG = "VEHICLE CAN'T BE NULL";
    public static final String MORE_THAN_ONE_DAY_MSG = "INPUT DATE SHOULD BE ONE DAY";
}
