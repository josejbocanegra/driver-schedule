package com.driverschedule.exceptions;

public final class ErrorMessage {
    public static final String DRIVER_NOT_FOUND = "The driver with the given id was not found";
    public static final String SCHEDULE_NOT_FOUND = "The schedule with the given id was not found";
    public static final String ALREADY_SCHEDULE = "There is a schedule in the selected slot";

    private ErrorMessage() {
        throw new IllegalStateException("Utility class");
    }
}
