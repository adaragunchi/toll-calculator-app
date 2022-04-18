package com.tollcalculator;

import com.tollcalculator.pojo.*;
import com.tollcalculator.service.impl.TollServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
public class TollFreeVehicleAndDatesTest {
    private final TollCalculator tollCalculator;
    private static LocalDate date;
    private static Vehicle car;
    public TollFreeVehicleAndDatesTest() {
        this.tollCalculator = new TollCalculator(new TollServiceImpl());
    }

    @BeforeAll
    private static void initDate() {
        date = LocalDate.now();
        car = new Car();
    }

    @Test
    @DisplayName("WEEKEND TOLL FREE TEST")
    public void weekEndTollFreeTest(){
        LocalDateTime time = LocalDateTime.of(2022, 04, 16, 15, 35);// SATURDAY
        assertEquals(0,  tollCalculator.getTollFee(car, time));
        assertEquals(0,  tollCalculator.getTollFee(car, time.plusDays(1)));// SUNDAY
    }

    @Test
    @DisplayName("HOLIDAY/FREE DATE TOLL FREE TEST")
    public void tollFreeDateTest(){
        LocalDateTime time = LocalDateTime.of(2022, 04, 30, 15, 35);// SATURDAY
        assertEquals(0,  tollCalculator.getTollFee(car, time));
    }

    @Test
    @DisplayName("TOLL FREE VEHICLE TEST")
    public void tollFreeVehicleTest(){
        Vehicle motorbike = new Motorbike();
        Vehicle tractor = new Tractor();
        Vehicle emergency = new Emergency();
        Vehicle foreign = new Foreign();
        Vehicle military = new Military();
        Vehicle diplomat = new Diplomat();

        LocalDateTime time = LocalDateTime.of(date, LocalTime.of(16, 40));
        assertEquals(0,  tollCalculator.getTollFee(motorbike, time));
        assertEquals(0,  tollCalculator.getTollFee(tractor, time));
        assertEquals(0,  tollCalculator.getTollFee(emergency, time));
        assertEquals(0,  tollCalculator.getTollFee(foreign, time));
        assertEquals(0,  tollCalculator.getTollFee(military, time));
        assertEquals(0,  tollCalculator.getTollFee(diplomat, time));
    }
}
