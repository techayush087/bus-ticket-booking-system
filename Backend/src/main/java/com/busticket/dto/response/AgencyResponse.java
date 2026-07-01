package com.busticket.dto.response;

import lombok.Builder;
import lombok.Data;

// AgencyResponse.java
@Data
@Builder
public class AgencyResponse {

    private Integer agencyId;
    private String name;
    private String contactPersonName;
    private String email;
    private String phone;
}
