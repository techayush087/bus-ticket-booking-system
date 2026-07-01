package com.busticket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.busticket.dto.response.RouteWithTripsDTO;
import com.busticket.entity.Route;
import com.busticket.entity.Trip;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.mapper.response.RouteWithTripsResponseMapper;
import com.busticket.respository.IRouteRepo;
import com.busticket.respository.ITripRepo;

import com.busticket.service.impl.RouteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RouteServiceImplTest {

    @Mock
    private IRouteRepo routeRepository;

    @Mock
    private ITripRepo tripRepository;

    @Mock
    private RouteWithTripsResponseMapper mapper;

    @InjectMocks
    private RouteServiceImpl routeService;

    private Route route;
    private Trip trip;

    @BeforeEach
    void setUp() {
        route = new Route();
        route.setRouteId(1);

        trip = new Trip();
    }

    @Test
    void testGetRouteWithTrips_Success() {

        List<Trip> trips = Arrays.asList(trip);

        RouteWithTripsDTO dto =
                new RouteWithTripsDTO(1, trips);

        when(routeRepository.findById(1)).thenReturn(Optional.of(route));
        when(tripRepository.findByRoute_RouteId(1)).thenReturn(trips);
        when(mapper.routeWithTripsToResponse(route, trips)).thenReturn(dto);

        RouteWithTripsDTO result = routeService.getRouteWithTrips(1);

        assertNotNull(result);
        assertEquals(1, result.getRouteId());
        assertEquals(1, result.getTrips().size());

        verify(routeRepository).findById(1);
        verify(tripRepository).findByRoute_RouteId(1);
        verify(mapper).routeWithTripsToResponse(route, trips);
    }

    @Test
    void testGetRouteWithTrips_RouteNotFound() {

        when(routeRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> routeService.getRouteWithTrips(1)
        );

        assertEquals("Route not found", exception.getMessage());

        verify(routeRepository).findById(1);
        verifyNoInteractions(tripRepository, mapper);
    }
}