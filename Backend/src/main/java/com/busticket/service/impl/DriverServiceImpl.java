package com.busticket.service.impl;

import org.springframework.stereotype.Service;

import com.busticket.dto.response.DriverScheduleResponse;
import com.busticket.dto.response.RouteResponse;
import com.busticket.dto.response.TripDetails;
import com.busticket.entity.Driver;
import com.busticket.entity.Route;
import com.busticket.entity.Trip;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.respository.IDriverRepo;
import com.busticket.respository.ITripRepo;
import com.busticket.service.interfaces.IDriverService;

import java.util.List;

@Service
public class DriverServiceImpl implements IDriverService{

    private final IDriverRepo driverRepository;
    private final ITripRepo tripRepository;
    
    public DriverServiceImpl(IDriverRepo driverRepository, ITripRepo tripRepository) {
    	this.driverRepository = driverRepository;
    	this.tripRepository = tripRepository;
    }

    public DriverScheduleResponse getDriverUpcomingTrips(Integer driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        List<Trip> trips = tripRepository.findUpcomingTripsByDriverId(driverId);

        List<TripDetails> tripDTOList = trips.stream()
                .map(DriverServiceImpl::mapToTripDTO)
                .toList();

        DriverScheduleResponse dto = new DriverScheduleResponse();
        dto.setDriverId(driver.getDriverId());
        dto.setDriverName(driver.getName());
        dto.setUpcomingTrips(tripDTOList);

        return dto;
    }

    public static TripDetails mapToTripDTO(Trip trip) {

        Route route = trip.getRoute();

        RouteResponse routeDTO = new RouteResponse();
        routeDTO.setRouteId(route.getRouteId());
        routeDTO.setFromCity(route.getFromCity());
        routeDTO.setToCity(route.getToCity());
        routeDTO.setDuration(route.getDuration());

        TripDetails tripDTO = new TripDetails();
        tripDTO.setTripId(trip.getTripId());
        tripDTO.setDepartureTime(trip.getDepartureTime());
        tripDTO.setArrivalTime(trip.getArrivalTime());
        tripDTO.setAvailableSeats(trip.getAvailableSeats());
        tripDTO.setFare(trip.getFare());
        tripDTO.setRoute(routeDTO);

        return tripDTO;
    }
}