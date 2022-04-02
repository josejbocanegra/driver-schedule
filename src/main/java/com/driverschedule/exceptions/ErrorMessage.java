package com.driverschedule.exceptions;

public final class ErrorMessage {
    public static final String DRIVER_NOT_FOUND = "The driver with the given id was not found";
    public static final String SCHEDULE_NOT_FOUND = "The schedule with the given id was not found";
    public static final String ALREADY_SCHEDULE = "There is a schedule in the selected slot";
    public static final String DRAG_LAT_OOR = "The drag latitude is out of range";
    public static final String DRAG_LNG_OOR = "The drag longitude is out of range";
    public static final String DROP_LAT_OOR = "The drop latitude is out of range";
    public static final String DROP_LNG_OOR = "The drop longitude is out of range";
    public static final String SLOT_NOT_AVAILABLE = "The slot is not availble";
    public static final String THERE_IS_NOT_SLOT = "There is not a slot in the selected date and time";
    public static final String COORDINATE_NOT_VALID = "There coordinate is not valid";

    private ErrorMessage() {
        throw new IllegalStateException("Utility class");
    }
}
