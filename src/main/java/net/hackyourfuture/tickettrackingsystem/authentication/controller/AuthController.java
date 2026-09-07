package net.hackyourfuture.tickettrackingsystem.authentication.controller;

import jakarta.validation.Valid;
import net.hackyourfuture.tickettrackingsystem.authentication.dto.AuthResponse;
import net.hackyourfuture.tickettrackingsystem.authentication.dto.LoginRequest;
import net.hackyourfuture.tickettrackingsystem.authentication.dto.RegisterRequest;
import net.hackyourfuture.tickettrackingsystem.authentication.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }
}