package com.busticket.service.impl;

import com.busticket.dto.response.BookingResponse;
import com.busticket.entity.Booking;
import com.busticket.entity.Payment;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.mapper.response.BookingResponseMapper;
import com.busticket.respository.IBookingRepo;
import com.busticket.respository.IPaymentRepo;
import com.busticket.service.interfaces.IBookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements IBookingService {

    private final IBookingRepo bookingRepo;
    private final IPaymentRepo paymentRepo;

    public BookingServiceImpl(IBookingRepo bookingRepo, IPaymentRepo paymentRepo) {
        this.bookingRepo = bookingRepo;
        this.paymentRepo = paymentRepo;
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepo.findAllWithPayment().stream()
                .map(row -> BookingResponseMapper.entityToResponse((Booking) row[0], (Payment) row[1]))
                .toList();
    }

    @Override
    public BookingResponse getBookingByID(Integer id) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        Payment payment = paymentRepo.findFirstByBooking_BookingId(id).orElse(null);
        return BookingResponseMapper.entityToResponse(booking, payment);
    }
}
