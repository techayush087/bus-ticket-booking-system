package com.busticket.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.busticket.entity.Driver;
import com.busticket.entity.Trip;

public interface IDriverRepo extends JpaRepository<Driver, Integer> {


}
