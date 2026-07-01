package com.busticket.mapper.response;

import com.busticket.dto.response.BookingResponse;
import com.busticket.entity.Booking;
import com.busticket.entity.Payment;
import com.busticket.entity.Route;
import com.busticket.entity.Trip;

public class BookingResponseMapper {

    public static BookingResponse entityToResponse(Booking booking, Payment payment) {
        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .tripId(booking.getTrip().getTripId())
                .fromCity(booking.getTrip().getRoute().getFromCity())
                .toCity(booking.getTrip().getRoute().getToCity())
                .departureTime(booking.getTrip().getDepartureTime())
                .arrivalTime(booking.getTrip().getArrivalTime())
                .fare(booking.getTrip().getFare())
                .seatNumber(booking.getSeatNumber())
                .status(booking.getStatus())
                .customerId(payment != null ? payment.getCustomer().getCustomerId() : null)
                .customerName(payment != null ? payment.getCustomer().getName() : null)
                .build();
    }


    public static Booking responseToEntity(BookingResponse response) {
        Route route = new Route();
        route.setFromCity(response.getFromCity());
        route.setToCity(response.getToCity());

        Trip trip = new Trip();
        trip.setTripId(response.getTripId());
        trip.setDepartureTime(response.getDepartureTime());
        trip.setArrivalTime(response.getArrivalTime());
        trip.setFare(response.getFare());
        trip.setRoute(route);

        return Booking.builder()
                .bookingId(response.getBookingId())
                .trip(trip)
                .seatNumber(response.getSeatNumber())
                .status(response.getStatus())
                .build();
    }
}
