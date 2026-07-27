package net.hackyourfuture.tickettrackingsystem.config.errorConfig;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}