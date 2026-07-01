package com.busticket.mapper.response;

import com.busticket.dto.response.AddressResponse;
import com.busticket.entity.Address;

public class AddressResponseMapper {

    public static AddressResponse entityToResponse(Address address) {
        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .address(address.getAddress())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    public static Address responseToEntity(AddressResponse response) {
        Address address = new Address();
        address.setAddressId(response.getAddressId());
        address.setAddress(response.getAddress());
        address.setCity(response.getCity());
        address.setState(response.getState());
        address.setZipCode(response.getZipCode());
        return address;
    }
}
