package com.busticket.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busticket.entity.Address;

public interface IAddressRepo extends JpaRepository<Address, Integer>{

}
