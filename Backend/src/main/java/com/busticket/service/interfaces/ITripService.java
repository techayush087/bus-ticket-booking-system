package com.busticket.service.interfaces;

import java.util.List;

import com.busticket.dto.response.DriverTripSummaryResponse;
import com.busticket.dto.response.TripFullDetailsResponse;
import com.busticket.dto.response.TripResponse;

public interface ITripService {
	
	public List<TripResponse> getTripsWithAvailableSeatsInARoute(int val,int id);
	public List<TripResponse> getTripsWithAvailableSeats(int val);
	public List<DriverTripSummaryResponse> getCompletedTrips(
            String fromCity,
            String toCity
    );
    public TripFullDetailsResponse getTripById(Integer tripId);

}



