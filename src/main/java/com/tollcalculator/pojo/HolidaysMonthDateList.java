package com.tollcalculator.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class HolidaysMonthDateList {
   List<HolidayMonthDateObj> holidayMonthDatesList;

   @Data
   @NoArgsConstructor
   public static class HolidayMonthDateObj{
      private int month;
      private List<Integer> dates;
   }
}
