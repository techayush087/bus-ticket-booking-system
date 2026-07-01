package com.busticket.service.interfaces;

import com.busticket.dto.response.DriverScheduleResponse;

public interface IDriverService{
	public DriverScheduleResponse getDriverUpcomingTrips(Integer driverId);

}
