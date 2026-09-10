package net.hackyourfuture.tickettrackingsystem.files.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import net.hackyourfuture.tickettrackingsystem.files.dto.FileResponse;
import net.hackyourfuture.tickettrackingsystem.files.dto.FileUploadRequest;
import net.hackyourfuture.tickettrackingsystem.files.dto.FileUploadResponse;
import net.hackyourfuture.tickettrackingsystem.files.services.FileService;

@RestController
@RequestMapping("/v1/api/tickets/{ticketId}/files")
public class FileController {

        private final FileService fileService;

        public FileController(FileService fileService) {
                this.fileService = fileService;
        }

        @GetMapping
        public ResponseEntity<List<FileResponse>> getFiles(
                        @PathVariable UUID ticketId) {

                return ResponseEntity.ok(
                                fileService.getFiles(ticketId));
        }

        @PreAuthorize("hasRole('USER')")
        @PostMapping("/upload")
        public ResponseEntity<FileUploadResponse> createUpload(
                        @PathVariable UUID ticketId,
                        @RequestBody FileUploadRequest request) {

                FileUploadResponse response = fileService.createUpload(
                                ticketId,
                                request);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(response);
        }

        @PreAuthorize("hasRole('USER')")
        @DeleteMapping("/{fileId}")
        public ResponseEntity<Void> delete(
                        @PathVariable UUID ticketId,
                        @PathVariable UUID fileId) {

                fileService.delete(
                                ticketId,
                                fileId);

                return ResponseEntity
                                .noContent()
                                .build();
        }

        @PreAuthorize("hasRole('USER')")
        @PostMapping("/{fileId}/complete")
        public ResponseEntity<Void> completeUpload(
                        @PathVariable UUID ticketId,
                        @PathVariable UUID fileId) {

                fileService.completeUpload(
                                ticketId,
                                fileId);

                return ResponseEntity.ok().build();
        }

        @PreAuthorize("hasRole('USER')")
        @PostMapping("/{fileId}/fail")
        public ResponseEntity<Void> failUpload(
                        @PathVariable UUID ticketId,
                        @PathVariable UUID fileId) {

                fileService.failUpload(
                                ticketId,
                                fileId);

                return ResponseEntity.ok().build();
        }
}