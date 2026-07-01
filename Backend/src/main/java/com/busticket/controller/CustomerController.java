package com.busticket.controller;

import com.busticket.dto.response.CustomerBookingPaymentResponse;
import com.busticket.dto.response.CustomerResponse;
import com.busticket.service.interfaces.ICustomerService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing customer-related operations.
 *
 * This controller provides APIs to:
 * - Fetch customer details by ID
 * - Fetch all customer bookings along with payment details
 *
 * It interacts with the service layer to process business logic
 * and returns responses in RESTful format.
 *
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final ICustomerService customerService;
    
    public CustomerController(ICustomerService customerService) {
    	this.customerService = customerService;
    }

    /**
     * Retrieves customer details along with address by customer ID.
     *
     * @param id the ID of the customer
     * @return ResponseEntity containing CustomerResponse DTO
     * @author Javed
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Integer id) {

        CustomerResponse response = customerService.getCustomerWithAddress(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all customers with their booking and payment details.
     *
     * @return list of CustomerBookingPaymentResponse DTOs
     * @author Shivam
     */
    @GetMapping("/bookings-payments")
    public List<CustomerBookingPaymentResponse> getAllCustomerBookingsPayments() {
        return customerService.getCustomerBookingsPayments();
    }
}