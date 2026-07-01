package com.busticket.controller;

import java.util.List;
import com.busticket.dto.response.TripFullDetailsResponse;
import com.busticket.service.interfaces.ITripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busticket.dto.response.DriverTripSummaryResponse;
import com.busticket.dto.response.TripResponse;

/**
 * REST Controller for managing trip-related operations.
 *
 * This controller provides APIs to:
 * - Fetch trips with available seats
 * - Fetch trips for a specific route
 * - Fetch completed trips between cities
 * - Fetch detailed trip information by ID
 *
 * It communicates with the service layer to process business logic
 * and returns responses in RESTful format.
 *
 */
@RestController
@RequestMapping("/api/trips")
public class TripsController {

	private final ITripService service;

	public TripsController(ITripService service) {
		this.service = service;
	}

	/**
	 * Retrieves trips with available seats for a specific route.
	 *
	 * @param id the route ID
	 * @return ResponseEntity containing list of TripResponse DTOs
	 * @author Deep
	 */
	@GetMapping("/route/{id}")
	public ResponseEntity<List<TripResponse>> getTripsWithAvailableSeatsInARoute(@PathVariable Integer id) {
	    List<TripResponse> trips = service.getTripsWithAvailableSeatsInARoute(0,id);
	    return ResponseEntity.ok(trips);
	}

	/**
	 * Retrieves all trips with available seats.
	 *
	 * @return ResponseEntity containing list of TripResponse DTOs
	 * @author Deep
	 */
	@GetMapping
	public ResponseEntity<List<TripResponse>> getTripsWithAvailableSeats() {
	    List<TripResponse> trips = service.getTripsWithAvailableSeats(0);
	    return ResponseEntity.ok(trips);
	}


	/**
	 * Retrieves completed trips between two cities.
	 *
	 * @param fromCity starting city
	 * @param toCity destination city
	 * @return list of DriverTripSummaryResponse DTOs
	 *  * @author Deep
	 */
    @GetMapping("/completed")
    public List<DriverTripSummaryResponse> getCompletedTrips(
            @RequestParam String fromCity,
            @RequestParam String toCity
    ) {
        return service.getCompletedTrips(fromCity, toCity);
    }

	/**
	 * Retrieves detailed information of a trip by ID.
	 *
	 * @param tripId the ID of the trip
	 * @return ResponseEntity containing TripFullDetailsResponse DTO
	 * @author Javed
	 */
    @GetMapping("/{tripId}")
    public ResponseEntity<TripFullDetailsResponse> getTripById(
            @PathVariable Integer tripId) {
    
        TripFullDetailsResponse trip = service.getTripById(tripId);
    
        return ResponseEntity.ok(trip);
    }
    
}



