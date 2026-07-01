package com.busticket.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busticket.entity.Bus;

public interface IBusRepo extends JpaRepository<Bus, Integer>{

}
