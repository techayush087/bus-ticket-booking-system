package com.busticket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.busticket.dto.response.*;
import com.busticket.entity.*;
import com.busticket.mapper.response.TripMapper;
import com.busticket.mapper.response.TripResponseMapper;
import com.busticket.respository.IRouteRepo;
import com.busticket.respository.ITripRepo;
import com.busticket.service.impl.TripServiceImpl;
import com.busticket.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TripServiceImplTest {

    @Mock
    private ITripRepo repo;
    
    @Mock
    private IRouteRepo routeRepo;

    @Mock
    private TripMapper tripMapper;

    @InjectMocks
    private TripServiceImpl service;

    private Trip trip1;
    private Trip trip2;
    private Trip trip3;

    @BeforeEach
    void setUp() {
        trip1 = new Trip();
        trip1.setTripId(1);
        trip1.setAvailableSeats(10);

        trip2 = new Trip();
        trip2.setTripId(2);
        trip2.setAvailableSeats(20);

        trip3 = new Trip();
        trip3.setTripId(3);
        trip3.setAvailableSeats(30);
    }

    @Test
    void testGetTripsWithAvailableSeats_Success() {

        List<Trip> trips = List.of(trip1, trip2, trip3);

        when(repo.findByAvailableSeatsGreaterThan(0)).thenReturn(trips);

        try (MockedStatic<TripResponseMapper> mockedMapper =
                     mockStatic(TripResponseMapper.class)) {

            mockedMapper.when(() -> TripResponseMapper.entityToResponse(trip1))
                    .thenReturn(TripResponse.builder()
                            .tripId(1)
                            .availableSeats(10)
                            .build());

            mockedMapper.when(() -> TripResponseMapper.entityToResponse(trip2))
                    .thenReturn(TripResponse.builder()
                            .tripId(2)
                            .availableSeats(20)
                            .build());

            mockedMapper.when(() -> TripResponseMapper.entityToResponse(trip3))
                    .thenReturn(TripResponse.builder()
                            .tripId(3)
                            .availableSeats(30)
                            .build());

            List<TripResponse> result = service.getTripsWithAvailableSeats(0);

            assertNotNull(result);
            assertEquals(3, result.size());
            assertEquals(1, result.get(0).getTripId());
            assertEquals(10, result.get(0).getAvailableSeats());
        }

        verify(repo).findByAvailableSeatsGreaterThan(0);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void testGetTripsWithAvailableSeats_NoTrips() {
        when(repo.findByAvailableSeatsGreaterThan(0)).thenReturn(List.of());

        List<TripResponse> result = service.getTripsWithAvailableSeats(0);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repo).findByAvailableSeatsGreaterThan(0);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void shouldReturnCombinedResults_whenBothDriversHaveTrips() {
        List<DriverTripSummaryResponse> list1 = List.of(
                new DriverTripSummaryResponse(1, "A", "Delhi", "Jaipur", 5L)
        );
        List<DriverTripSummaryResponse> list2 = List.of(
                new DriverTripSummaryResponse(2, "B", "Delhi", "Jaipur", 3L)
        );

        when(repo.getTripsByDriver1("Delhi", "Jaipur")).thenReturn(list1);
        when(repo.getTripsByDriver2("Delhi", "Jaipur")).thenReturn(list2);

        List<DriverTripSummaryResponse> result =
                service.getCompletedTrips("Delhi", "Jaipur");

        assertEquals(2, result.size());

        verify(repo).getTripsByDriver1("Delhi", "Jaipur");
        verify(repo).getTripsByDriver2("Delhi", "Jaipur");
    }

    @Test
    void shouldHandleNullResponseFromRepository() {
        when(repo.getTripsByDriver1("Delhi", "Jaipur")).thenReturn(null);
        when(repo.getTripsByDriver2("Delhi", "Jaipur")).thenReturn(null);

        List<DriverTripSummaryResponse> result =
                service.getCompletedTrips("Delhi", "Jaipur");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnFullTripDetails_whenTripExists() {
        Integer tripId = 1;

        Trip trip = mock(Trip.class);
        Route route = mock(Route.class);
        Bus bus = mock(Bus.class);
        Driver driver = mock(Driver.class);

        RouteResponse routeResponse = RouteResponse.builder().build();
        BusResponse busResponse = BusResponse.builder().build();

        AddressResponse addressResponse = AddressResponse.builder()
                .addressId(10)
                .city("Delhi")
                .state("Delhi")
                .zipCode("110001")
                .address("Street")
                .build();

        DriverResponse driverResponse = DriverResponse.builder()
                .driverId(1)
                .name("John")
                .licenseNumber("LIC123")
                .phone("9999999999")
                .officeId(101)
                .agencyId(201)
                .address(addressResponse)
                .build();

        TripFullDetailsResponse expectedResponse =
                TripFullDetailsResponse.builder()
                        .tripId(tripId)
                        .route(routeResponse)
                        .bus(busResponse)
                        .drivers(List.of(driverResponse))
                        .build();

        when(repo.findById(tripId)).thenReturn(Optional.of(trip));
        when(trip.getRoute()).thenReturn(route);
        when(trip.getBus()).thenReturn(bus);
        when(trip.getDriver1()).thenReturn(driver);
        when(trip.getDriver2()).thenReturn(null);

        when(tripMapper.tripToFullDetailsResponse(
                eq(trip),
                eq(route),
                eq(bus),
                anyList()
        )).thenReturn(expectedResponse);

        TripFullDetailsResponse result = service.getTripById(tripId);

        assertNotNull(result);
        assertEquals(tripId, result.getTripId());
        assertEquals(1, result.getDrivers().size());
        assertEquals("John", result.getDrivers().get(0).getName());

        verify(repo).findById(tripId);
        verify(tripMapper).tripToFullDetailsResponse(eq(trip), eq(route), eq(bus), anyList());
    }

    @Test
    void shouldThrowException_whenTripNotFound() {
        Integer tripId = 99;

        when(repo.findById(tripId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getTripById(tripId));

        verify(repo).findById(tripId);
    }
    
    @Test
    void shouldReturnTrips_whenRouteExistsAndTripsAvailable() {

        int routeId = 1;
        int minSeats = 5;

        Trip trip = new Trip();
        trip.setTripId(1);
        trip.setAvailableSeats(10);

        List<Trip> trips = List.of(trip);

        TripResponse response = TripResponse.builder()
                .tripId(1)
                .availableSeats(10)
                .build();

        when(routeRepo.existsById(routeId)).thenReturn(true);

        when(repo.findByAvailableSeatsGreaterThanAndRoute_RouteId(minSeats, routeId))
                .thenReturn(trips);

        try (MockedStatic<TripResponseMapper> mockedMapper = mockStatic(TripResponseMapper.class)) {
            mockedMapper.when(() -> TripResponseMapper.entityToResponse(trip))
                    .thenReturn(response);

            List<TripResponse> result =
                    service.getTripsWithAvailableSeatsInARoute(minSeats, routeId);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(1, result.get(0).getTripId());
        }

        verify(routeRepo).existsById(routeId);
        verify(repo).findByAvailableSeatsGreaterThanAndRoute_RouteId(minSeats, routeId);
    }
    
    @Test
    void shouldThrowException_whenRouteNotFound() {

        int routeId = 99;
        int minSeats = 5;

        when(routeRepo.existsById(routeId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.getTripsWithAvailableSeatsInARoute(minSeats, routeId));

        verify(routeRepo).existsById(routeId);
        verifyNoInteractions(repo); 
    }
}