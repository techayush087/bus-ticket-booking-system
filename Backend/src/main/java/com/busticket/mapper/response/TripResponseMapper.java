package com.busticket.mapper.response;

import com.busticket.dto.response.TripResponse;
import com.busticket.entity.Address;
import com.busticket.entity.Bus;
import com.busticket.entity.Driver;
import com.busticket.entity.Route;
import com.busticket.entity.Trip;

public class TripResponseMapper {

    public static TripResponse entityToResponse(Trip trip) {
        return TripResponse.builder()
                .tripId(trip.getTripId())
                // Route
                .routeId(trip.getRoute().getRouteId())
                .fromCity(trip.getRoute().getFromCity())
                .toCity(trip.getRoute().getToCity())
                .breakPoints(trip.getRoute().getBreakPoints())
                .durationFormatted(trip.getRoute().getDuration() + "h 0m")
                // Bus
                .busId(trip.getBus().getBusId())
                .busRegistrationNumber(trip.getBus().getRegistrationNumber())
                .busType(trip.getBus().getType())
                .busCapacity(trip.getBus().getCapacity())
                // Boarding address
                .boardingAddressId(trip.getBoardingAddress().getAddressId())
                .boardingAddress(trip.getBoardingAddress().getAddress())
                .boardingCity(trip.getBoardingAddress().getCity())
                // Dropping address
                .droppingAddressId(trip.getDroppingAddress().getAddressId())
                .droppingAddress(trip.getDroppingAddress().getAddress())
                .droppingCity(trip.getDroppingAddress().getCity())
                // Driver 1
                .driver1Id(trip.getDriver1().getDriverId())
                .driver1Name(trip.getDriver1().getName())
                .driver1Phone(trip.getDriver1().getPhone())
                // Driver 2
                .driver2Id(trip.getDriver2().getDriverId())
                .driver2Name(trip.getDriver2().getName())
                .driver2Phone(trip.getDriver2().getPhone())
                // Trip details
                .departureTime(trip.getDepartureTime())
                .arrivalTime(trip.getArrivalTime())
                .tripDate(trip.getTripDate())
                .availableSeats(trip.getAvailableSeats())
                .fare(trip.getFare())
                .build();
    }

    public static Trip responseToEntity(TripResponse response) {
        Trip trip = new Trip();
        trip.setTripId(response.getTripId());
        trip.setDepartureTime(response.getDepartureTime());
        trip.setArrivalTime(response.getArrivalTime());
        trip.setTripDate(response.getTripDate());
        trip.setAvailableSeats(response.getAvailableSeats());
        trip.setFare(response.getFare());

        Route route = new Route();
        route.setRouteId(response.getRouteId());
        route.setFromCity(response.getFromCity());
        route.setToCity(response.getToCity());
        route.setBreakPoints(response.getBreakPoints());
        trip.setRoute(route);

        Bus bus = new Bus();
        bus.setBusId(response.getBusId());
        bus.setRegistrationNumber(response.getBusRegistrationNumber());
        bus.setType(response.getBusType());
        bus.setCapacity(response.getBusCapacity());
        trip.setBus(bus);

        Address boarding = new Address();
        boarding.setAddressId(response.getBoardingAddressId());
        boarding.setAddress(response.getBoardingAddress());
        boarding.setCity(response.getBoardingCity());
        trip.setBoardingAddress(boarding);

        Address dropping = new Address();
        dropping.setAddressId(response.getDroppingAddressId());
        dropping.setAddress(response.getDroppingAddress());
        dropping.setCity(response.getDroppingCity());
        trip.setDroppingAddress(dropping);

        Driver driver1 = new Driver();
        driver1.setDriverId(response.getDriver1Id());
        driver1.setName(response.getDriver1Name());
        driver1.setPhone(response.getDriver1Phone());
        trip.setDriver1(driver1);

        Driver driver2 = new Driver();
        driver2.setDriverId(response.getDriver2Id());
        driver2.setName(response.getDriver2Name());
        driver2.setPhone(response.getDriver2Phone());
        trip.setDriver2(driver2);

        return trip;
    }
}
