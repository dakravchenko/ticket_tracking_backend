package net.hackyourfuture.tickettrackingsystem.authentication.dto;

public record LoginRequest(
        String email,
        String password) {

}
