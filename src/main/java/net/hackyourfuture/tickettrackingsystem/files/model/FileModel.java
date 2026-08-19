package net.hackyourfuture.tickettrackingsystem.files.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FileModel {

    private UUID fileId;
    private UUID ticketId;
    private String fileName;
    private String storageKey;
    private String contentType;
    private long fileSize;
    private LocalDateTime createdAt;
    private String status;
}