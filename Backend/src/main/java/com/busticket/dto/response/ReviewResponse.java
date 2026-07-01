// ReviewResponse.java
package com.busticket.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponse {

    private Integer reviewId;

    // Customer summary
    private Integer customerId;
    private String customerName;
    private String customerEmail;

    // Trip summary
    private Integer tripId;
    private String fromCity;
    private String toCity;
    private LocalDateTime departureTime;

    private Integer rating;
    private String comment;
    private LocalDateTime reviewDate;
}