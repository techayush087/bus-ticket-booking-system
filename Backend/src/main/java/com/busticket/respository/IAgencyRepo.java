package com.busticket.respository;

import com.busticket.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAgencyRepo extends JpaRepository<Agency, Integer> {

	@Query("""
		    SELECT DISTINCT o.agency
		    FROM AgencyOffice o
		    JOIN o.address addr
		    WHERE LOWER(addr.city) = LOWER(:city)
		""")
	public List<Agency> findAgenciesByCity(String city);
}