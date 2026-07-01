package com.busticket.service.interfaces;

import com.busticket.dto.response.AgencyResponse;
import java.util.List;

public interface IAgencyService {
    public List<AgencyResponse> getAgenciesByCity(String city);
}