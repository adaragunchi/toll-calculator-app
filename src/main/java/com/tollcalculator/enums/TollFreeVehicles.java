package com.tollcalculator.enums;

import com.tollcalculator.constants.TollCalculatorConstants;

/**
 * This is enum class to return toll free vehicles
 */
public enum TollFreeVehicles {
    MOTORBIKE(TollCalculatorConstants.MOTORBIKE),
    TRACTOR(TollCalculatorConstants.TRACTOR),
    EMERGENCY(TollCalculatorConstants.EMERGENCY),
    DIPLOMAT(TollCalculatorConstants.DIPLOMAT),
    FOREIGN(TollCalculatorConstants.FOREIGN),
    MILITARY(TollCalculatorConstants.MILITARY);
    private final String type;

    TollFreeVehicles(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
