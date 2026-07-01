package com.busticket.controller;


import com.busticket.dto.response.FeatureResponse;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @GetMapping("/dashboard")
    @JsonPropertyOrder({"name", "features"})
    public Map<String, Object> dashboard(Authentication auth) {

        String email = auth.getName();

        System.out.println(email);


        Map<String, String> names = Map.of(
                "javed@gmail.com", "Mohammad Javed",
                "shivam@gmail.com", "Shivam",
                "ayush@gmail.com", "Ayush",
                "shreshtha@gmail.com", "Shrestha",
                "deep@gmail.com", "Deep"
        );

        // 👇 feature mapping
        Map<String, List<FeatureResponse>> devFeatures = Map.of(

                "javed@gmail.com", List.of(
                        new FeatureResponse("Get Customer Details", "/api/customers/{id}"),
                        new FeatureResponse("Get Trip Details", "/api/trips/{trip_id}")
                ),

                "shivam@gmail.com", List.of(
                        new FeatureResponse("Get Agencies in a City", "/api/agencies?city={city}"),
                        new FeatureResponse("Get Customer Bookings and Payments", "/api/customers/bookings-payments")
                ),

                "ayush@gmail.com" , List.of(
                        new FeatureResponse("Get Route with Trips", "/api/routes/{route_id}/trips"),
                        new FeatureResponse("Get agency offices with their bus and drivers", "/agencies/offices/resources")
                ),

                "deep@gmail.com", List.of(
                        new FeatureResponse("Get Trip with seat availability", "/api/trips"),
                        new FeatureResponse("Get driver schedule (their trips and routes)", "/api/drivers/{driver_id}/upcoming-trips"),
                        new FeatureResponse("Get total trips completed by drivers from boarding address and dropping address", "/api/trips/completed?fromCity={from_city}&toCity={to_city}"),
                        new FeatureResponse("Get Trip with seat availability in a route", "/api/trips/route/{id}")
                 ),


                "shreshtha@gmail.com" , List.of(
                        new FeatureResponse("Get Bookings and their details", "/api/bookings/"),
                        new FeatureResponse("Get trip reviews with customer id", "/api/trips/customer/{customer_id}/reviews")
                )
        );

        return Map.of(
                "name", names.get(email),
                "features", devFeatures.getOrDefault(email, List.of())
        );
    }
}