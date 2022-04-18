package com.tollcalculator.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalTime;

@Data
@Accessors(chain = true)
public class FeeByTimeInterval {
    private LocalTime start;
    private LocalTime end;
    private double fee;
}
