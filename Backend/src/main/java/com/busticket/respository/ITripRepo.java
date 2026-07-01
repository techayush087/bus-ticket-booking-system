package com.busticket.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.busticket.dto.response.DriverTripSummaryResponse;
import com.busticket.entity.Trip;

@Repository
public interface ITripRepo extends JpaRepository<Trip, Integer> {

	@Query
	public List<Trip> findByAvailableSeatsGreaterThan(int val);
	
	public List<Trip> findByAvailableSeatsGreaterThanAndRoute_RouteId(int val,int id);

	@Query("""
			    SELECT t FROM Trip t
			    WHERE
			        (t.driver1.driverId = :driverId
			         OR t.driver2.driverId = :driverId)
			    AND t.departureTime > CURRENT_TIMESTAMP
			""")
	public List<Trip> findUpcomingTripsByDriverId(@Param("driverId") Integer driverId);
	
	@Query("""
		    SELECT new com.busticket.dto.response.DriverTripSummaryResponse(
		        d.driverId,
		        d.name,
		        r.fromCity,
		        r.toCity,
		        COUNT(t.tripId)
		    )
		    FROM Trip t
		    JOIN t.route r
		    JOIN t.driver1 d
		    WHERE r.fromCity = :fromCity
		      AND r.toCity = :toCity
		      AND t.departureTime < CURRENT_TIMESTAMP
		    GROUP BY d.driverId, d.name, r.fromCity, r.toCity
		""")
		List<DriverTripSummaryResponse> getTripsByDriver1(
		        @Param("fromCity") String fromCity,
		        @Param("toCity") String toCity
		);
	
	@Query("""
		    SELECT new com.busticket.dto.response.DriverTripSummaryResponse(
		        d.driverId,
		        d.name,
		        r.fromCity,
		        r.toCity,
		        COUNT(t.tripId)
		    )
		    FROM Trip t
		    JOIN t.route r
		    JOIN t.driver2 d
		    WHERE r.fromCity = :fromCity
		      AND r.toCity = :toCity
		      AND t.departureTime < CURRENT_TIMESTAMP
		    GROUP BY d.driverId, d.name, r.fromCity, r.toCity
		""")
		List<DriverTripSummaryResponse> getTripsByDriver2(
		        @Param("fromCity") String fromCity,
		        @Param("toCity") String toCity
		);



	public List<Trip> findByRoute_RouteId(Integer routeId);

    @EntityGraph(attributePaths = {"bus", "route"})
    public Optional<Trip> findByTripId(Integer tripId);
   
}

