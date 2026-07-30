package net.hackyourfuture.tickettrackingsystem.email.dto;

import java.util.List;

public record EmailRequest(
    List<String> recipients,
    String subject,
    String html
) {
}