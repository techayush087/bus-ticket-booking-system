package com.busticket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import com.busticket.dto.response.AgencyResponse;
import com.busticket.entity.Agency;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.respository.IAgencyRepo;
import com.busticket.service.impl.AgencyServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AgencyServiceImplTest {

    @Mock
    private IAgencyRepo agencyRepository;

    @InjectMocks
    private AgencyServiceImpl agencyService;

    private Agency agency1;
    private Agency agency2;

    @BeforeEach
    void setUp() {
        agency1 = new Agency();
        agency1.setAgencyId(1);
        agency1.setName("Delhi Travels");

        agency2 = new Agency();
        agency2.setAgencyId(2);
        agency2.setName("Capital Bus Service");
    }

    @Test
    void testGetAgenciesByCity_Success() {

        List<Agency> mockList = List.of(agency1, agency2);

        when(agencyRepository.findAgenciesByCity("Delhi"))
                .thenReturn(mockList);

        List<AgencyResponse> result = agencyService.getAgenciesByCity("Delhi");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Delhi Travels", result.get(0).getName());

        verify(agencyRepository).findAgenciesByCity("Delhi");
    }

    @Test
    void testGetAgenciesByCity_NoData() {

        when(agencyRepository.findAgenciesByCity("Delhi"))
                .thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> agencyService.getAgenciesByCity("Delhi")
        );

        assertEquals("No agencies found in given city", exception.getMessage());

        verify(agencyRepository).findAgenciesByCity("Delhi");
    }
}