package com.busticket.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busticket.entity.AgencyOffice;

public interface IAgencyOfficeRepo extends JpaRepository<AgencyOffice, Integer>{

}
