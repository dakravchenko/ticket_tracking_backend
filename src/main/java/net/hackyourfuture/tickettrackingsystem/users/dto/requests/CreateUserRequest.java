package net.hackyourfuture.tickettrackingsystem.users.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "Name is required") @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters") String name,

        @NotBlank(message = "Email is required") @Size(max = 100, message = "Email must be at most 100 characters") @Email(message = "Email must be valid")

        String email) {
}
