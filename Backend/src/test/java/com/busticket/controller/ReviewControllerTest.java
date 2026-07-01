package com.busticket.controller;

import com.busticket.entity.Customer;
import com.busticket.entity.Review;
import com.busticket.entity.Route;
import com.busticket.entity.Trip;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.service.interfaces.IReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Mock
    private IReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private Review sampleReview() {
        Customer customer = Customer.builder()
                .customerId(1)
                .name("Alice")
                .email("alice@example.com")
                .phone("8888888888")
                .build();

        Route route = new Route();
        route.setFromCity("Delhi");
        route.setToCity("Agra");

        Trip trip = Trip.builder()
                .tripId(5)
                .route(route)
                .departureTime(LocalDateTime.of(2025, 7, 1, 9, 0))
                .build();

        return Review.builder()
                .reviewId(1)
                .customer(customer)
                .trip(trip)
                .rating(5)
                .comment("Great ride!")
                .reviewDate(LocalDateTime.of(2025, 7, 1, 15, 0))
                .build();
    }

    @Test
    void getReviewsByCustomerId_shouldReturnMappedReviews_whenCustomerExists() {
        when(reviewService.findReviewByCustomerId(1)).thenReturn(List.of(sampleReview()));

        var response = reviewController.getReviewsByCustomerId(1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Alice", response.getBody().get(0).getCustomerName());
        assertEquals("Delhi", response.getBody().get(0).getFromCity());
        assertEquals(5, response.getBody().get(0).getRating());
        verify(reviewService, times(1)).findReviewByCustomerId(1);
    }

    @Test
    void getReviewsByCustomerId_shouldReturnEmptyList_whenNoReviews() {
        when(reviewService.findReviewByCustomerId(2)).thenReturn(List.of());

        var response = reviewController.getReviewsByCustomerId(2);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().isEmpty());
        verify(reviewService, times(1)).findReviewByCustomerId(2);
    }

    @Test
    void getReviewsByCustomerId_shouldThrowException_whenCustomerNotFound() {
        when(reviewService.findReviewByCustomerId(99))
                .thenThrow(new ResourceNotFoundException("Customer not found"));

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> reviewController.getReviewsByCustomerId(99)
        );

        assertEquals("Customer not found", ex.getMessage());
        verify(reviewService, times(1)).findReviewByCustomerId(99);
    }
}
