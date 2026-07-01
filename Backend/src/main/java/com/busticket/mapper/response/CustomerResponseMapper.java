package com.busticket.mapper.response;

import com.busticket.dto.response.CustomerResponse;
import com.busticket.entity.Customer;

public class CustomerResponseMapper {

    public static CustomerResponse entityToResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(AddressResponseMapper.entityToResponse(customer.getAddress()))
                .build();
    }

    public static Customer responseToEntity(CustomerResponse response) {
        Customer customer = new Customer();
        customer.setCustomerId(response.getCustomerId());
        customer.setName(response.getName());
        customer.setEmail(response.getEmail());
        customer.setPhone(response.getPhone());
        customer.setAddress(AddressResponseMapper.responseToEntity(response.getAddress()));
        return customer;
    }
}
