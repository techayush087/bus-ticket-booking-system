package com.busticket.service.impl;

import com.busticket.entity.Review;
import com.busticket.respository.IReviewRepo;
import com.busticket.service.interfaces.IReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements IReviewService {

    private final IReviewRepo reviewRepo;

    public ReviewServiceImpl(IReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Override
    public List<Review> findReviewByCustomerId(Integer customerId) {
        return reviewRepo.findByCustomer_CustomerId(customerId);
    }
}
