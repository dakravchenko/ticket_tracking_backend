package net.hackyourfuture.tickettrackingsystem.email.dto;

import java.util.List;

public record ResendRequest(
    String from,
    List<String> to,
    String subject,
    String html
) {
}