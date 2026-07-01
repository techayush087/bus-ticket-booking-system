package com.busticket.service.interfaces;

import com.busticket.entity.Review;

import java.util.List;

public interface IReviewService {
    List<Review> findReviewByCustomerId(Integer customerId);
}
