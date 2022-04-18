package com.tollcalculator.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class HolidayMonthDates {
    private Integer month;
    private List<Integer> dates;
}
