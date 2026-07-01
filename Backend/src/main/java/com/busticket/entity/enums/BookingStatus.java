package com.busticket.entity.enums;

public enum BookingStatus {

    Available("Available"),
    Booked("Booked");

    private final String message;

    BookingStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static BookingStatus fromValue(String value) {
        return BookingStatus.valueOf(value);
    }
}
