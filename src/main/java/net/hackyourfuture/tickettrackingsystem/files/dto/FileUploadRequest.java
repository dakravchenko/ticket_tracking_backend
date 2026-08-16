package net.hackyourfuture.tickettrackingsystem.files.dto;

public record FileUploadRequest(
        String fileName,
        String contentType,
        long fileSize
) {
}