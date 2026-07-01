package com.busticket.mapper.response;

import com.busticket.dto.response.ReviewResponse;
import com.busticket.entity.Customer;
import com.busticket.entity.Review;
import com.busticket.entity.Route;
import com.busticket.entity.Trip;

public class ReviewResponseMapper {

    public static ReviewResponse entityToResponse(Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .customerId(review.getCustomer().getCustomerId())
                .customerName(review.getCustomer().getName())
                .customerEmail(review.getCustomer().getEmail())
                .tripId(review.getTrip().getTripId())
                .fromCity(review.getTrip().getRoute().getFromCity())
                .toCity(review.getTrip().getRoute().getToCity())
                .departureTime(review.getTrip().getDepartureTime())
                .rating(review.getRating())
                .comment(review.getComment())
                .reviewDate(review.getReviewDate())
                .build();
    }

    public static Review responseToEntity(ReviewResponse response) {
        Review review = new Review();
        review.setReviewId(response.getReviewId());
        review.setRating(response.getRating());
        review.setComment(response.getComment());
        review.setReviewDate(response.getReviewDate());

        Customer customer = new Customer();
        customer.setCustomerId(response.getCustomerId());
        customer.setName(response.getCustomerName());
        customer.setEmail(response.getCustomerEmail());
        review.setCustomer(customer);

        Route route = new Route();
        route.setFromCity(response.getFromCity());
        route.setToCity(response.getToCity());

        Trip trip = new Trip();
        trip.setTripId(response.getTripId());
        trip.setDepartureTime(response.getDepartureTime());
        trip.setRoute(route);
        review.setTrip(trip);

        return review;
    }
}
