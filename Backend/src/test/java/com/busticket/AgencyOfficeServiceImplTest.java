package com.busticket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.busticket.dto.response.AgencyOfficeWithResourcesDTO;
import com.busticket.entity.AgencyOffice;
import com.busticket.entity.Bus;
import com.busticket.entity.Driver;
import com.busticket.respository.IAgencyOfficeRepo;
import com.busticket.respository.IBusRepo;
import com.busticket.respository.IDriverRepo;

import com.busticket.service.impl.AgencyOfficeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AgencyOfficeServiceImplTest {

    @Mock
    private IAgencyOfficeRepo agencyOfficeRepository;

    @Mock
    private IBusRepo busRepository;

    @Mock
    private IDriverRepo driverRepository;

    @InjectMocks
    private AgencyOfficeServiceImpl service;

    private AgencyOffice office;
    private Bus bus;
    private Driver driver;

    @BeforeEach
    void setUp() {
        office = new AgencyOffice();
        office.setOfficeId(1);
        office.setOfficeContactPersonName("John");

        bus = new Bus();
        bus.setOffice(office);

        driver = new Driver();
        driver.setAgencyOffice(office);
    }

    @Test
    void testGetAgencyOfficesWithResources_Success() {

        when(agencyOfficeRepository.findAll())
                .thenReturn(Arrays.asList(office));

        when(busRepository.findAll())
                .thenReturn(Arrays.asList(bus));

        when(driverRepository.findAll())
                .thenReturn(Arrays.asList(driver));

        List<AgencyOfficeWithResourcesDTO> result =
                service.getAgencyOfficesWithResources();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getBuses().size());
        assertEquals(1, result.get(0).getDrivers().size());

        verify(agencyOfficeRepository).findAll();
        verify(busRepository).findAll();
        verify(driverRepository).findAll();
    }

    @Test
    void testGetAgencyOfficesWithResources_EmptyData() {

        when(agencyOfficeRepository.findAll()).thenReturn(List.of());
        when(busRepository.findAll()).thenReturn(List.of());
        when(driverRepository.findAll()).thenReturn(List.of());

        List<AgencyOfficeWithResourcesDTO> result =
                service.getAgencyOfficesWithResources();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(agencyOfficeRepository).findAll();
        verify(busRepository).findAll();
        verify(driverRepository).findAll();
    }
}
