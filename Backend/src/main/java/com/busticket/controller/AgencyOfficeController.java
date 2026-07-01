package com.busticket.controller;


import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busticket.dto.response.AgencyOfficeWithResourcesDTO;
import com.busticket.service.interfaces.IAgencyOfficeService;

import lombok.AllArgsConstructor;

/**
 * REST Controller for managing Agency Office resources.
 *
 * This controller provides APIs to fetch agency office details
 * along with associated resources such as buses and drivers.
 *
 * It interacts with the service layer to retrieve and process data.
 *
 * @author Ayush
 */
@RestController
public class AgencyOfficeController {

	private final IAgencyOfficeService agencyService;
	
	public AgencyOfficeController(IAgencyOfficeService agencyService) {
		this.agencyService =agencyService;
	}

	/**
	 * Fetches all agency offices along with their associated resources.
	 *
	 * @return ResponseEntity containing list of AgencyOfficeWithResourcesDTO
	 */
	    @GetMapping("/agencies/offices/resources")
	    public ResponseEntity<List<AgencyOfficeWithResourcesDTO>> getAgencyOfficesWithResources() {

	        List<AgencyOfficeWithResourcesDTO> officesWithResources =
	                agencyService.getAgencyOfficesWithResources();

	        return ResponseEntity.ok(officesWithResources);
	    }
	}
	
	
