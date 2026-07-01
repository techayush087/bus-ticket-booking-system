package com.busticket.controller;

import com.busticket.dto.response.AddressResponse;
import com.busticket.dto.response.CustomerResponse;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.service.interfaces.ICustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private ICustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void getCustomerById_shouldReturnCustomer_whenCustomerExists() {
        AddressResponse address = AddressResponse.builder()
                .addressId(1)
                .address("MG Road")
                .city("Bangalore")
                .state("Karnataka")
                .zipCode("560001")
                .build();

        CustomerResponse expected = CustomerResponse.builder()
                .customerId(10)
                .name("John Doe")
                .email("john@example.com")
                .phone("9999999999")
                .address(address)
                .build();

        when(customerService.getCustomerWithAddress(10)).thenReturn(expected);

        ResponseEntity<CustomerResponse> response = customerController.getCustomerById(10);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(10, response.getBody().getCustomerId());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals("john@example.com", response.getBody().getEmail());
        verify(customerService, times(1)).getCustomerWithAddress(10);
    }

    @Test
    void getCustomerById_shouldThrowException_whenCustomerDoesNotExist() {
        when(customerService.getCustomerWithAddress(99))
                .thenThrow(new ResourceNotFoundException("Customer not found"));

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> customerController.getCustomerById(99)
        );

        assertEquals("Customer not found", ex.getMessage());
        verify(customerService, times(1)).getCustomerWithAddress(99);
    }
}
