package com.busticket.controller;

import com.busticket.dto.response.BusResponse;
import com.busticket.dto.response.DriverResponse;
import com.busticket.dto.response.RouteResponse;
import com.busticket.dto.response.TripFullDetailsResponse;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.service.interfaces.ITripService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripsControllerTest {

    @Mock
    private ITripService tripService;

    @InjectMocks
    private TripsController tripsController;

    @Test
    void getTripById_shouldReturnTrip_whenTripExists() {
        RouteResponse route = RouteResponse.builder()
                .routeId(1)
                .fromCity("Delhi")
                .toCity("Agra")
                .breakPoints(2)
                .duration(180)
                .durationFormatted("3h 0m")
                .build();

        BusResponse bus = BusResponse.builder()
                .busId(5)
                .registrationNumber("KA01AB1234")
                .type("AC")
                .capacity(40)
                .officeId(11)
                .officeMail("office@example.com")
                .officeContactPersonName("Office Manager")
                .agencyId(100)
                .agencyName("Best Travels")
                .build();

        DriverResponse driver = DriverResponse.builder()
                .driverId(7)
                .name("Rahul")
                .phone("8888888888")
                .licenseNumber("LIC123")
                .build();

        TripFullDetailsResponse expected = TripFullDetailsResponse.builder()
                .tripId(1)
                .route(route)
                .bus(bus)
                .drivers(List.of(driver))
                .build();

        // IMPORTANT: use Integer.valueOf(...) to hit the Integer overload
        when(tripService.getTripById(Integer.valueOf(1))).thenReturn(expected);

        ResponseEntity<TripFullDetailsResponse> response = tripsController.getTripById(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTripId());
        assertEquals("Delhi", response.getBody().getRoute().getFromCity());
        assertEquals("Agra", response.getBody().getRoute().getToCity());
        verify(tripService, times(1)).getTripById(Integer.valueOf(1));
    }

    @Test
    void getTripById_shouldThrowException_whenTripDoesNotExist() {
        when(tripService.getTripById(Integer.valueOf(99)))
                .thenThrow(new ResourceNotFoundException("Trip not found with ID: 99"));

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> tripsController.getTripById(99)
        );

        assertEquals("Trip not found with ID: 99", ex.getMessage());
        verify(tripService, times(1)).getTripById(Integer.valueOf(99));
    }
}