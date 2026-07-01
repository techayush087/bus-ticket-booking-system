package com.busticket.respository;

import com.busticket.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepo extends JpaRepository<Booking, Integer> {

    @Query("SELECT b FROM Booking b JOIN FETCH b.trip t JOIN FETCH t.route WHERE t.tripId = :id")
    List<Booking> findByTrip_TripId(@Param("id") Integer id);

    @Query("SELECT b, p FROM Booking b LEFT JOIN Payment p ON p.booking.bookingId = b.bookingId")
    List<Object[]> findAllWithPayment();

}
