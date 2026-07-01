package com.busticket.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.busticket.dto.response.AgencyOfficeWithResourcesDTO;
import com.busticket.entity.Bus;
import com.busticket.entity.Driver;
import com.busticket.entity.AgencyOffice;
import com.busticket.respository.IAgencyOfficeRepo;
import com.busticket.respository.IBusRepo;
import com.busticket.respository.IDriverRepo;
import com.busticket.service.interfaces.IAgencyOfficeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgencyOfficeServiceImpl implements IAgencyOfficeService {

    private final IAgencyOfficeRepo agencyOfficeRepository;
    private final IBusRepo busRepository;
    private final IDriverRepo driverRepository;

    @Override
    public List<AgencyOfficeWithResourcesDTO> getAgencyOfficesWithResources() {

        List<AgencyOffice> offices = agencyOfficeRepository.findAll();
        List<Bus> buses = busRepository.findAll();
        List<Driver> drivers = driverRepository.findAll();

        Map<Integer, List<Bus>> busesByOffice =
                buses.stream()
                     .collect(Collectors.groupingBy(
                         bus -> bus.getOffice().getOfficeId()
                     ));
        Map<Integer, List<Driver>> driversByOffice =
                drivers.stream()
                       .collect(Collectors.groupingBy(
                           driver -> driver.getAgencyOffice().getOfficeId()
                       ));

        return offices.stream()
                .map(office -> new AgencyOfficeWithResourcesDTO(
                        office.getOfficeId(),
                        office.getOfficeContactPersonName(),
                        busesByOffice.getOrDefault(office.getOfficeId(), Collections.emptyList()),
                        driversByOffice.getOrDefault(office.getOfficeId(), Collections.emptyList())
                ))
                .collect(Collectors.toList());
    }
}
