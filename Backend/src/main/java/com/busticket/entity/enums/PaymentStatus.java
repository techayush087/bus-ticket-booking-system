package com.busticket.entity.enums;

public enum PaymentStatus {

    Success("Success"),
    Failed("Failed");

    private final String message;

    PaymentStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static PaymentStatus fromValue(String value) {
        return PaymentStatus.valueOf(value);
    }
}
