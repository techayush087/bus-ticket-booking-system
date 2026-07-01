package com.busticket.service.interfaces;

import java.util.List;

import com.busticket.dto.response.CustomerBookingPaymentResponse;
import com.busticket.dto.response.CustomerResponse;

public interface ICustomerService {
    public CustomerResponse getCustomerWithAddress(Integer customerId);
    
    public List<CustomerBookingPaymentResponse> getCustomerBookingsPayments();
}
