// DriverResponse.java
package com.busticket.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverResponse {

    private Integer driverId;
    private String licenseNumber;
    private String name;
    private String phone;

    // Office summary — flat to avoid deep nesting
    private Integer officeId;
    private String officeMail;
    private String officeContactPersonName;

    // Agency summary
    private Integer agencyId;
    private String agencyName;

    // Full address object in response
    private AddressResponse address;
}