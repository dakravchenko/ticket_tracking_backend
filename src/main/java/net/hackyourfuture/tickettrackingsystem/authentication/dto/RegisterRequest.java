package net.hackyourfuture.tickettrackingsystem.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Name is required") @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters") String name,
        @Email @NotBlank String email,
        @NotBlank @Size(min = 8, max = 100) String password
) {}