package com.busticket.respository;

import com.busticket.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepo extends JpaRepository<Review, Integer> {

    List<Review> findByCustomer_CustomerId(Integer customerId);
}
