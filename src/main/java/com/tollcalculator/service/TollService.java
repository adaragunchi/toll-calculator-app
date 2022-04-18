package com.tollcalculator.service;

import com.tollcalculator.pojo.Vehicle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface TollService {
    boolean isTollFreeDay(LocalDate day);
    double getTollFee(LocalTime time);
    boolean isValid(Vehicle vehicle, LocalDateTime... dates);
}
