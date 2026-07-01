package com.busticket.service.impl;

import com.busticket.dto.response.AgencyResponse;
import com.busticket.entity.Agency;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.mapper.response.AgencyResponseMapper;
import com.busticket.respository.IAgencyRepo;
import com.busticket.service.interfaces.IAgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AgencyServiceImpl implements IAgencyService {

    private final IAgencyRepo agencyRepository;

    @Override
    public List<AgencyResponse> getAgenciesByCity(String city) {

        List<Agency> agencies = agencyRepository.findAgenciesByCity(city);
        
        if (agencies.isEmpty()) {
            throw new ResourceNotFoundException("No agencies found in given city");
        }

        return agencies.stream()
                .map(AgencyResponseMapper::entityToResponse)
                .toList();
    }
}