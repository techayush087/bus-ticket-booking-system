package com.busticket.controller;

import com.busticket.dto.response.BookingResponse;
import com.busticket.entity.enums.BookingStatus;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.service.interfaces.IBookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private IBookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private BookingResponse sampleBooking() {
        return BookingResponse.builder()
                .bookingId(1)
                .tripId(10)
                .fromCity("Mumbai")
                .toCity("Pune")
                .departureTime(LocalDateTime.of(2025, 6, 1, 8, 0))
                .arrivalTime(LocalDateTime.of(2025, 6, 1, 11, 0))
                .fare(new BigDecimal("350.00"))
                .seatNumber(5)
                .status(BookingStatus.Booked)
                .customerId(100)
                .customerName("Jane Doe")
                .build();
    }

    @Test
    void getAllBookings_shouldReturnList() {
        when(bookingService.getAllBookings()).thenReturn(List.of(sampleBooking()));

        ResponseEntity<List<BookingResponse>> response = bookingController.getAllBookings();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Mumbai", response.getBody().get(0).getFromCity());
        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    void getBookingById_shouldReturnBooking_whenExists() {
        when(bookingService.getBookingByID(1)).thenReturn(sampleBooking());

        ResponseEntity<BookingResponse> response = bookingController.getBookingById(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getBookingId());
        assertEquals(BookingStatus.Booked, response.getBody().getStatus());
        verify(bookingService, times(1)).getBookingByID(1);
    }

    @Test
    void getBookingById_shouldThrowException_whenNotFound() {
        when(bookingService.getBookingByID(99))
                .thenThrow(new ResourceNotFoundException("Booking not found"));

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> bookingController.getBookingById(99)
        );

        assertEquals("Booking not found", ex.getMessage());
        verify(bookingService, times(1)).getBookingByID(99);
    }
}
