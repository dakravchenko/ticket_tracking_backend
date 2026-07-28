package net.hackyourfuture.tickettrackingsystem.projects.response;

public record ProjectSummaryResponse(
        String projectName,
        int totalTickets,
        int openTickets,
        int inProgressTickets,
        int closedTickets) {

}
