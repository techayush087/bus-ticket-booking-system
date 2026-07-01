package com.busticket.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

// AddressResponse.java
@Data
@Builder
@JsonPropertyOrder({
        "addressId",
        "address",
        "city",
        "state",
        "zipCode"
})
public class AddressResponse {

    private Integer addressId;
    private String address;
    private String city;
    private String state;
    private String zipCode;
}
