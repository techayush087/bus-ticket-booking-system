package com.busticket.respository;

import com.busticket.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.busticket.dto.response.CustomerBookingPaymentResponse;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPaymentRepo extends JpaRepository<Payment, Integer> {
    @Query("SELECT p FROM Payment p WHERE p.booking.bookingId IN :bookingIds")
    public List<Payment> findByBookingIds(@Param("bookingIds") List<Integer> bookingIds);
    public Optional<Payment> findFirstByBooking_BookingId(Integer id);
    
    
    public Optional<Payment> findByBooking_BookingId(Integer bookingId);
    
    @Query("""
            SELECT new com.busticket.dto.response.CustomerBookingPaymentResponse(
                c.name,
                c.email,
                b.bookingId,
                b.seatNumber,
                t.tripId,
                p.amount,
                p.paymentStatus
            )
            FROM Payment p
            JOIN p.customer c
            JOIN p.booking b
            JOIN b.trip t
        """)
      public  List<CustomerBookingPaymentResponse> getCustomerBookingPayments();
}
