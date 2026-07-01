package com.busticket.service.impl;

import com.busticket.dto.response.CustomerBookingPaymentResponse;
import com.busticket.dto.response.CustomerResponse;
import com.busticket.entity.Address;
import com.busticket.entity.Customer;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.mapper.response.CustomerResponseMapper;
import com.busticket.respository.ICustomerRepo;
import com.busticket.respository.IPaymentRepo;
import com.busticket.service.interfaces.ICustomerService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepo customerRepository;
    private final IPaymentRepo paymentRepository;
    
    public CustomerServiceImpl(ICustomerRepo customerRepository,IPaymentRepo paymentRepository) {
    	this.customerRepository = customerRepository;
    	this.paymentRepository = paymentRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerWithAddress(Integer customerId) {
        Customer customer = customerRepository.findByIdWithAddress(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer id: "+ customerId));

        if (customer.getAddress() == null) {
            customer.setAddress(new Address()); // avoids NPE
        }

        return CustomerResponseMapper.entityToResponse(customer);
    }

    @Override
    public List<CustomerBookingPaymentResponse> getCustomerBookingsPayments() {
    
    	List<CustomerBookingPaymentResponse> list =
                paymentRepository.getCustomerBookingPayments();

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No booking payment data found");
        }

        return list;
    }
}
