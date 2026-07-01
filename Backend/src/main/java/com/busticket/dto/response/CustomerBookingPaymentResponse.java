package com.busticket.dto.response;

import com.busticket.entity.enums.PaymentStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerBookingPaymentResponse {

    private String customerName;
    private String email;
    private Integer bookingId;
    private Integer seatNumber;
    private Integer tripId;
    private Double amount;
    private PaymentStatus paymentStatus;   

    public CustomerBookingPaymentResponse(
            String customerName,
            String email,
            Integer bookingId,
            Integer seatNumber,
            Integer tripId,
            Double amount,
            PaymentStatus paymentStatus) {

        this.customerName = customerName;
        this.email = email;
        this.bookingId = bookingId;
        this.seatNumber = seatNumber;
        this.tripId = tripId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }
}