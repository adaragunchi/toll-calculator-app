package com.tollcalculator.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class FeeByTimeIntervalList {
    private List<TimeFeeObj> feeByTimeIntervalList;

    @Data
    @NoArgsConstructor
    public static class TimeFeeObj{
        private String startTime;
        private String endTime;
        private Double fee;
    }
}
