package com.busticket.controller;

import com.busticket.dto.response.AgencyResponse;
import com.busticket.service.interfaces.IAgencyService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing agency-related operations.
 *
 * This controller provides APIs to retrieve agency details
 * based on specific filters such as city.
 *
 * It interacts with the service layer to fetch business data
 * and returns it as a REST response.
 *
 * @author Shivam
 */
@RestController
@RequestMapping("/api/agencies")
public class AgencyController {

    private final IAgencyService agencyService;
    
    public AgencyController(IAgencyService agencyService) {
    	this.agencyService = agencyService;
    }

    /**
     * Retrieves a list of agencies based on the given city.
     *
     * @param city the name of the city to filter agencies
     * @return list of AgencyResponse DTOs matching the city
     */
    @GetMapping
    public List<AgencyResponse> getAgenciesByCity(
            @RequestParam String city) {

        return agencyService.getAgenciesByCity(city);
    }
}