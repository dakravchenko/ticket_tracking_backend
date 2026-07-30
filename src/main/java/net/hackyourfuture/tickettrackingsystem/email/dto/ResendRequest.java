package net.hackyourfuture.tickettrackingsystem.email.dto;

public record ResendRequest(
        String from,
        String to,
        String subject,
        String html) {
}