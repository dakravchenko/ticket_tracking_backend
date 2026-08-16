package net.hackyourfuture.tickettrackingsystem.files.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import net.hackyourfuture.tickettrackingsystem.config.errorConfig.ResourceNotFoundException;
import net.hackyourfuture.tickettrackingsystem.files.dao.FileDao;
import net.hackyourfuture.tickettrackingsystem.files.dto.FileResponse;
import net.hackyourfuture.tickettrackingsystem.files.dto.FileUploadRequest;
import net.hackyourfuture.tickettrackingsystem.files.dto.FileUploadResponse;
import net.hackyourfuture.tickettrackingsystem.files.model.FileModel;
import net.hackyourfuture.tickettrackingsystem.tickets.dao.TicketDao;

@Service
public class FileService {

    private final FileDao fileDao;
    private final TicketDao ticketDao;
    private final B2StorageService storageService;

    public FileService(
            FileDao fileDao,
            TicketDao ticketDao,
            B2StorageService storageService) {

        this.fileDao = fileDao;
        this.ticketDao = ticketDao;
        this.storageService = storageService;
    }

    public FileUploadResponse createUpload(
            UUID ticketId,
            FileUploadRequest request) {

        checkTicketExists(ticketId);

        String objectId = UUID.randomUUID().toString();

        String storageKey = "tickets/"
                + ticketId
                + "/"
                + objectId
                + "-"
                + request.fileName();

        FileModel file = fileDao.create(
                ticketId,
                request.fileName(),
                storageKey,
                request.contentType(),
                request.fileSize());

        String uploadUrl = storageService.generateUploadUrl(
                storageKey,
                request.contentType());

        return new FileUploadResponse(
                file.getFileId(),
                uploadUrl);
    }

    public List<FileResponse> getFiles(
            UUID ticketId) {

        checkTicketExists(ticketId);

        List<FileModel> files = fileDao.findByTicketId(ticketId);

        return files.stream()
                .map(file -> new FileResponse(
                        file.getFileId(),
                        file.getTicketId(),
                        file.getFileName(),
                        file.getContentType(),
                        file.getFileSize(),
                        file.getCreatedAt(),
                        storageService.generateDownloadUrl(
                                file.getStorageKey())))
                .toList();
    }

    public void delete(
            UUID ticketId,
            UUID fileId) {

        FileModel file = getFileForTicket(ticketId, fileId);

        storageService.delete(
                file.getStorageKey());

        fileDao.delete(fileId);
    }

    private FileModel getFileForTicket(
            UUID ticketId,
            UUID fileId) {

        FileModel file = fileDao.findById(fileId);

        if (file == null ||
                !file.getTicketId().equals(ticketId)) {

            throw new ResourceNotFoundException(
                    "File not found");
        }

        return file;
    }

    private void checkTicketExists(
            UUID ticketId) {

        if (ticketDao.getTicketById(ticketId) == null) {
            throw new ResourceNotFoundException(
                    "Ticket with id "
                            + ticketId
                            + " not found");
        }
    }
}