// BusResponse.java
package com.busticket.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BusResponse {

    private Integer busId;

    // Office summary — flat fields to avoid deep nesting
    private Integer officeId;
    private String officeMail;
    private String officeContactPersonName;

    // Agency summary
    private Integer agencyId;
    private String agencyName;

    private String registrationNumber;
    private Integer capacity;
    private String type;
}