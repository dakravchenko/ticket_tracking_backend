package net.hackyourfuture.tickettrackingsystem.tickets.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import net.hackyourfuture.tickettrackingsystem.config.validation.ValidEnum;
import net.hackyourfuture.tickettrackingsystem.enums.StatusEnum;

public record TicketUpdateRequest(
        @NotBlank(message = "Title is required") @Size(min = 3, max = 100, message = "Title must be longer than 3 characters and less than 100 characters") String title,
        String description,
        @NotNull(message = "Project ID is required") UUID projectId,
        @ValidEnum(enumClass = StatusEnum.class, message = "Status must be one of: OPEN, IN_PROGRESS, CLOSED") String status) {
}
