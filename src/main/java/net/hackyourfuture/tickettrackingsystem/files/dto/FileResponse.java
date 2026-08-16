package net.hackyourfuture.tickettrackingsystem.files.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record FileResponse(
        UUID fileId,
        UUID ticketId,
        String fileName,
        String contentType,
        long fileSize,
        LocalDateTime createdAt,
        String url
) {
}