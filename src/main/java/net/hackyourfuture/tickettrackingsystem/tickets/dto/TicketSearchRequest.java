package net.hackyourfuture.tickettrackingsystem.tickets.dto;

import net.hackyourfuture.tickettrackingsystem.enums.StatusEnum;

public record TicketSearchRequest(
    String text,
    StatusEnum status
) {
}
