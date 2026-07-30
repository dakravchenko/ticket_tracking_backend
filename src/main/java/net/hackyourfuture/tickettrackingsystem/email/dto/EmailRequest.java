package net.hackyourfuture.tickettrackingsystem.email.dto;


public record EmailRequest(
    String recipients,
    String subject,
    String html
) {
}