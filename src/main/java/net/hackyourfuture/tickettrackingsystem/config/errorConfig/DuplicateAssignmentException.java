package net.hackyourfuture.tickettrackingsystem.config.errorConfig;

public class DuplicateAssignmentException extends RuntimeException {
    public DuplicateAssignmentException(String message) {
        super(message);
    }
}