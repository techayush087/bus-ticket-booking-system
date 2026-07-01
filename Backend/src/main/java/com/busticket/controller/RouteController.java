package com.busticket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.busticket.dto.response.RouteWithTripsDTO;
import com.busticket.service.interfaces.IRouteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final IRouteService routeService;

    /**
     * REST Controller for managing route-related operations.
     *
     * This controller provides APIs to fetch route details
     * along with associated trips.
     *
     * It communicates with the service layer to process business logic
     * and returns structured REST responses.
     *
     * @author Ayush
     */
    public RouteController(IRouteService routeService) {
    	this.routeService = routeService;
    }

    /**
     * Retrieves route details along with associated trips for a given route ID.
     *
     * @param routeId the ID of the route
     * @return ResponseEntity containing RouteWithTripsDTO
     */
    @GetMapping("/{routeId}/trips")
    public ResponseEntity<RouteWithTripsDTO> getRouteWithTrips(
            @PathVariable Integer routeId) {

        RouteWithTripsDTO response = routeService.getRouteWithTrips(routeId);

        return ResponseEntity.ok(response);
    }
}