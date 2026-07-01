package com.busticket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    @NotNull
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @NotNull
    private Trip trip;

    @Column(name = "rating", nullable = false)
    @NotNull
    private Integer rating;

    @Column(name = "comment")
    @NotNull
    private String comment;

    @Column(name = "review_date")
    @NotNull
    private LocalDateTime reviewDate;
}