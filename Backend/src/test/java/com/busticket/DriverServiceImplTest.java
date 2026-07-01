package com.busticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.busticket.dto.response.DriverScheduleResponse;
import com.busticket.entity.Driver;
import com.busticket.entity.Route;
import com.busticket.entity.Trip;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.respository.IDriverRepo;
import com.busticket.respository.ITripRepo;
import com.busticket.service.impl.DriverServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DriverServiceImplTest {
	
	@Mock
    private IDriverRepo driverRepository;
	
	@Mock
	private ITripRepo tripRepository;
	
	@InjectMocks
	private DriverServiceImpl service;
	
	private Driver driver1;
	private Driver driver2;
	private Trip trip1;
	private Trip trip2;
	private Trip trip3;
	
	@BeforeEach
	void setUp() {

	    driver1 = new Driver();
	    driver1.setDriverId(1);
	    driver1.setName("Raman");

	    driver2 = new Driver();
	    driver2.setDriverId(2);
	    driver2.setName("Anuj");

	    // Initialize trips
	    trip1 = new Trip();
	    trip1.setTripId(101);
	    trip1.setAvailableSeats(10);

	    Route route1 = new Route();
	    route1.setRouteId(1);
	    route1.setFromCity("Delhi");
	    route1.setToCity("Jaipur");
	    route1.setDuration(5);

	    trip1.setRoute(route1);

	    trip2 = new Trip();
	    trip2.setTripId(102);
	    trip2.setAvailableSeats(20);
	    trip2.setRoute(route1);

	    trip3 = new Trip();
	    trip3.setTripId(103);
	    trip3.setAvailableSeats(30);
	    trip3.setRoute(route1);
	}
	
	@Test
	void testGetDriverUpcomingTrips_Success() {
		Optional<Driver> opDriver1 =  Optional.of(driver1);
		List<Trip> trips = List.of(trip1,trip2,trip3);
		when(driverRepository.findById(1)).thenReturn(opDriver1);
		when(tripRepository.findUpcomingTripsByDriverId(1)).thenReturn(trips);
		
		DriverScheduleResponse result = service.getDriverUpcomingTrips(1);
		assertNotNull(result);
		assertEquals(1, result.getDriverId());
		assertEquals("Raman", result.getDriverName());
		assertEquals(trip1.getTripId(), result.getUpcomingTrips().get(0).getTripId());
		verify(driverRepository).findById(1);
		verify(tripRepository).findUpcomingTripsByDriverId(1);
		verifyNoMoreInteractions(driverRepository, tripRepository);
	}
	
	@Test
	void testGetDriverUpcomingTrips_ThrowsException() {
		when(driverRepository.findById(2)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, ()->service.getDriverUpcomingTrips(2));
		
	}
	
	@Test
	void testGetDriverUpcomingTrips_NoUpcomingTrips() {
		Optional<Driver> opDriver1 =  Optional.of(driver1);
		when(driverRepository.findById(1)).thenReturn(opDriver1);
		when(tripRepository.findUpcomingTripsByDriverId(1)).thenReturn(List.of());
		
		DriverScheduleResponse result = service.getDriverUpcomingTrips(1);
		assertNotNull(result);
		assertEquals(1, result.getDriverId());
		assertEquals("Raman", result.getDriverName());
		assertEquals(0, result.getUpcomingTrips().size());
		verify(driverRepository).findById(1);
		verify(tripRepository).findUpcomingTripsByDriverId(1);
		verifyNoMoreInteractions(driverRepository, tripRepository);
	}
	
}
