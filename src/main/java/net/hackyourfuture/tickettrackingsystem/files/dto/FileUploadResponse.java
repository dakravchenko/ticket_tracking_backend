package net.hackyourfuture.tickettrackingsystem.files.dto;

import java.util.UUID;

public record FileUploadResponse(
        UUID fileId,
        String uploadUrl
) {
}