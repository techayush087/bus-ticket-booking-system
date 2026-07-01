package com.busticket.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busticket.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepo extends JpaRepository<Customer, Integer>{
    @Query("""
            SELECT c FROM Customer c
            LEFT JOIN FETCH c.address
            WHERE c.customerId = :customerId
            """)
    Optional<Customer> findByIdWithAddress(@Param("customerId") Integer customerId);
}
