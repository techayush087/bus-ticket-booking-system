package com.busticket.service.interfaces;

import java.util.List;

import com.busticket.dto.response.AgencyOfficeWithResourcesDTO;

public interface IAgencyOfficeService {

	
    public List<AgencyOfficeWithResourcesDTO> getAgencyOfficesWithResources();
}
