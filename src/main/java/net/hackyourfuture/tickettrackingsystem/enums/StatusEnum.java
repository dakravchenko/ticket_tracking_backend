package net.hackyourfuture.tickettrackingsystem.enums;

public enum StatusEnum {
    OPEN("open"),
    IN_PROGRESS("in_progress"),
    CLOSED("closed");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
