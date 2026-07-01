package com.busticket.controller;

import com.busticket.dto.response.BookingResponse;
import com.busticket.service.interfaces.IBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing booking-related operations.
 *
 * This controller provides APIs to retrieve booking details,
 * including fetching all bookings and fetching a booking by its ID.
 *
 * It communicates with the service layer to handle business logic
 * and returns responses in a RESTful format.
 *
 * @author Shreshtha
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Retrieves all bookings.
     *
     * @return ResponseEntity containing list of BookingResponse DTOs
     */
    @GetMapping("/")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    /**
     * Retrieves booking details by booking ID.
     *
     * @param id the ID of the booking
     * @return ResponseEntity containing BookingResponse DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.getBookingByID(id));
    }
}
