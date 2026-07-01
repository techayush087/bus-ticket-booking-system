package com.busticket.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busticket.entity.Route;

public interface IRouteRepo extends JpaRepository<Route, Integer>{

}
