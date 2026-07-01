package com.busticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import com.busticket.dto.response.CustomerBookingPaymentResponse;
import com.busticket.entity.enums.PaymentStatus;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.respository.IPaymentRepo;
import com.busticket.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private IPaymentRepo paymentRepo;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerBookingPaymentResponse response;

    @BeforeEach
    void setUp() {
    	 response = new CustomerBookingPaymentResponse(
    	            "Shivam",
    	            "shivam@gmail.com",
    	            1,              
    	            12,
    	            101,            
    	            500.00,         
    	            PaymentStatus.Success); 
    }

    @Test
    void testGetCustomerBookingPayments_Success() {

        List<CustomerBookingPaymentResponse> mockList = List.of(response);

        when(paymentRepo.getCustomerBookingPayments())
                .thenReturn(mockList);

        List<CustomerBookingPaymentResponse> result =
                customerService.getCustomerBookingsPayments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Shivam", result.get(0).getCustomerName());

        verify(paymentRepo).getCustomerBookingPayments();
    }

    @Test
    void testGetCustomerBookingPayments_NoData() {

        when(paymentRepo.getCustomerBookingPayments())
                .thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.getCustomerBookingsPayments()
        );

        assertEquals("No booking payment data found", exception.getMessage());

        verify(paymentRepo).getCustomerBookingPayments();
    }
}