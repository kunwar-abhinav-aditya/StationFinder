package com.stationfinder.task.exception;

import lombok.Data;

@Data
public class ChargingStationExceptionSchema {
    private String message;
    private String details;
    private String hint;

    protected ChargingStationExceptionSchema() {}

    public ChargingStationExceptionSchema(
            String message, String details, String hint) {
        this.message = message;
        this.details = details;
        this.hint = hint;
    }
}
