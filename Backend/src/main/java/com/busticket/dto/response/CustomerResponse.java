// CustomerResponse.java
package com.busticket.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({
        "customerId",
        "name",
        "email",
        "phone",
        "address"
})
public class CustomerResponse {

    private Integer customerId;
    private String name;
    private String email;
    private String phone;
    private AddressResponse address;    // full address object in response
}