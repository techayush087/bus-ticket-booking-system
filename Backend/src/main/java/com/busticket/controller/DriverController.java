package com.busticket.controller;

import org.springframework.web.bind.annotation.*;

import com.busticket.dto.response.DriverScheduleResponse;
import com.busticket.service.interfaces.IDriverService;

/**
 * REST Controller for managing driver-related operations.
 *
 * This controller provides APIs to fetch driver schedules,
 * including upcoming trips assigned to a specific driver.
 *
 * It communicates with the service layer to retrieve data
 * and returns it as a REST response.
 *
 * @author Deep
 */
@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final IDriverService driverService;
    
    public DriverController(IDriverService driverService) {
    	this.driverService = driverService;
    }

    /**
     * Retrieves upcoming trips for a given driver.
     *
     * @param driverId the ID of the driver
     * @return ResponseEntity containing DriverScheduleResponse DTO
     */
    @GetMapping("/{driverId}/upcoming-trips")
    public DriverScheduleResponse getUpcomingTrips(@PathVariable Integer driverId) {
        return driverService.getDriverUpcomingTrips(driverId);
    }
}