package com.hubject.task.exception;

import lombok.Data;

@Data
public class ChargingStationException extends RuntimeException {
    private String message;
    private String details;
    private String hint;

    protected ChargingStationException() {}

    public ChargingStationException(
            String message, String details, String hint) {
        this.message = message;
        this.details = details;
        this.hint = hint;
    }
}
