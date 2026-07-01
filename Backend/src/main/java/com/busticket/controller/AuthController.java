package com.busticket.controller;

import com.busticket.entity.Login;
import com.busticket.entity.User;
import com.busticket.service.impl.JwtService;
import com.busticket.service.interfaces.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final IUserService userService;

    public AuthController(JwtService jwtService, IUserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login request) {

        System.out.println(request.getEmail());
        System.out.println(request.getPassword());
        User user = userService.getUser(request.getEmail());

        if (!request.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of("token", token));
    }
}