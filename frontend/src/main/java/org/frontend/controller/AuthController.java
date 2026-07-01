package org.frontend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
public class AuthController {

    private static final String API_BASE = "http://localhost:8085";
    private final RestTemplate restTemplate;

    public AuthController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  PUBLIC PAGES
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  LOGIN
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {
        try {
            Map<String, String> body = Map.of("email", email.trim(), "password", password);


            ResponseEntity<Map<String, Object>> res = restTemplate.exchange(
                    API_BASE + "/api/auth/login",
                    HttpMethod.POST,
                    new HttpEntity<>(body),
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> responseBody = res.getBody();
            if (responseBody == null) {
                model.addAttribute("error", "Login failed");
                return "login";
            }

            String token = (String) responseBody.get("token");
            if (token == null || token.isBlank()) {
                model.addAttribute("error", "Login failed");
                return "login";
            }

            session.setAttribute("token", token);
            session.setAttribute("email", email.trim());
            return "redirect:/dashboard";

        } catch (Exception e) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  DASHBOARD
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");

        if (token == null) return "redirect:/login";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            ResponseEntity<Map<String, Object>> res = restTemplate.exchange(
                    API_BASE + "/api/dashboard",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> data = res.getBody();

            model.addAttribute("name", data.get("name"));
            model.addAttribute("features", data.get("features"));
            return "dashboard";

        } catch (Exception e) {
            session.invalidate();
            return "redirect:/login";
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  API VIEW — ROUTE TO SPECIFIC TEMPLATES
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    @GetMapping("/api-view")
    public String apiView(@RequestParam String endpoint,
                          @RequestParam(defaultValue = "API Response") String title,
                          Model model,
                          HttpSession session) {

        model.addAttribute("title", title);
        model.addAttribute("endpoint", endpoint);

        try {
            String token = (String) session.getAttribute("token");
            System.out.println("Token in session: " + token);

            HttpHeaders headers = new HttpHeaders();
            if (token != null && !token.isBlank()) {
                headers.set("Authorization", "Bearer " + token);
            }

            System.out.println(API_BASE+endpoint);

            ResponseEntity<Object> res = restTemplate.exchange(
                    API_BASE + endpoint,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Object.class
            );

            Object data = res.getBody();
            return resolveView(endpoint, data, model);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "views/error-view";
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  VIEW RESOLVER — ALL 12 ENDPOINTS
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    @SuppressWarnings("unchecked")
    private String resolveView(String ep, Object data, Model m) {

        if (data == null) {
            m.addAttribute("jsonData", "{}");
            return "views/generic-view";
        }

        // ── SINGLE OBJECT RESPONSES ──

        // 1. GET /api/trips/{id} --> correct
        if (ep.matches("/api/trips/\\d+/?")) {
            if (data instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) data;
                m.addAttribute("trip", map);
                m.addAttribute("bus", map.get("bus"));
                m.addAttribute("route", map.get("route"));
                m.addAttribute("drivers", map.get("drivers"));
                return "views/trip-detail";
            }
        }

        // 2. GET /api/customers/{id} --> correct
        if (ep.matches("/api/customers/\\d+/?")) {
            if (data instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) data;
                m.addAttribute("customer", map);
                m.addAttribute("address", map.get("address"));
                return "views/customer-detail";
            }
        }

        // 3. GET /api/routes/{id}/trips --> correct
        if (ep.matches("/api/routes/\\d+/trips/?")) {
            if (data instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) data;
                m.addAttribute("routeId", map.get("routeId"));
                m.addAttribute("trips", map.get("trips"));
                return "views/route-trips";
            }
        }

        // 4. GET /api/drivers/{id}/upcoming-trips -> correct
        if (ep.matches("/api/drivers/\\d+/upcoming-trips/?")) {
            if (data instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) data;
                m.addAttribute("driver", map);
                m.addAttribute("upcomingTrips", map.get("upcomingTrips"));
                return "views/driver-upcoming-trips";
            }
        }

        // ── LIST RESPONSES ──

        // 5. GET /agencies/offices/resources -> correct
        if (ep.contains("/agencies/offices/resources")) {
            m.addAttribute("offices", toList(data));
            return "views/office-resources";
        }

        // 6. GET /api/trips/completed (with query params)
        if (ep.startsWith("/api/trips/completed")) {
            m.addAttribute("tripCounts", toList(data));  // ← correct model attribute
            return "views/driver-trip-count";            // ← correct view
        }

        // 7. GET /api/trips/route/{id}
        if (ep.matches("/api/trips/route/\\d+/?")) {
            m.addAttribute("trips", toList(data));
            return "views/trips-list";
        }

        // 8. GET /api/trips (must come AFTER more specific /api/trips/* patterns)
        if (ep.equals("/api/trips") || ep.equals("/api/trips/")) {
            m.addAttribute("trips", toList(data));
            return "views/trips-list";
        }

        // 9. GET /api/agencies (with query params like ?city=...)
        if (ep.startsWith("/api/agencies")) {
            m.addAttribute("agencies", toList(data));
            return "views/agencies-list";
        }
        // In resolveView() — ADD this BEFORE the generic trips check



        // 10. GET /api/customers/bookings-payments
        if (ep.contains("bookings-payments")) {
            m.addAttribute("bookingPayments", toList(data));
            return "views/booking-payments";
        }

        // 11. GET /api/bookings/
        if (ep.startsWith("/api/bookings")) {
            m.addAttribute("bookings", toList(data));
            return "views/bookings-list";
        }

        // 12. GET /api/trips/customer/{id}/reviews
        if (ep.contains("/reviews")) {
            m.addAttribute("reviews", toList(data));
            return "views/reviews-list";
        }

        // ── FALLBACK ──
        m.addAttribute("jsonData", data.toString());
        return "views/generic-view";
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  HELPER
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> toList(Object data) {
        if (data instanceof List) {
            List<?> rawList = (List<?>) data;
            List<Map<String, Object>> result = new ArrayList<>();

            for (Object item : rawList) {
                if (item instanceof Map) {
                    result.add((Map<String, Object>) item);
                }
            }
            return result;
        }

        if (data instanceof Map) {
            return List.of((Map<String, Object>) data);
        }

        return List.of();
    }
}