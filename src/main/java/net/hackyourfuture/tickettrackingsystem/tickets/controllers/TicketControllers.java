package net.hackyourfuture.tickettrackingsystem.tickets.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import net.hackyourfuture.tickettrackingsystem.enums.StatusEnum;
import net.hackyourfuture.tickettrackingsystem.tickets.dto.TicketCreateRequest;
import net.hackyourfuture.tickettrackingsystem.tickets.dto.TicketSearchRequest;
import net.hackyourfuture.tickettrackingsystem.tickets.dto.TicketUpdateRequest;
import net.hackyourfuture.tickettrackingsystem.tickets.model.TicketModel;
import net.hackyourfuture.tickettrackingsystem.tickets.services.TicketServices;
import net.hackyourfuture.tickettrackingsystem.users.model.UserModel;

@RestController
@RequestMapping("/v1/api/tickets")
public class TicketControllers {
    private final TicketServices ticketService;

    public TicketControllers(TicketServices ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketModel> createTicket(@Valid @RequestBody TicketCreateRequest ticket) {
        TicketModel createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @GetMapping
    public ResponseEntity<List<TicketModel>> searchTickets(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) StatusEnum status) {

        TicketSearchRequest request = new TicketSearchRequest(text, status);

        List<TicketModel> tickets = ticketService.searchTickets(request);

        for (TicketModel ticket : tickets) {
            if (ticket != null) {
                List<UserModel> assignedUsers = ticketService.getAssignees(ticket.getTicketId());
                ticket.setAssignees(assignedUsers);
            }
        }

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketModel> getTicketById(@PathVariable UUID id) {
        TicketModel ticket = ticketService.getTicketById(id);
        List<UserModel> assignedUsers = ticketService.getAssignees(id);

        if (ticket != null) {
            ticket.setAssignees(assignedUsers);
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketModel> updateTicket(@PathVariable UUID id,
            @Valid @RequestBody TicketUpdateRequest ticket) {
        TicketModel updatedTicket = ticketService.updateTicket(id, ticket);

        if (updatedTicket != null) {
            return ResponseEntity.ok(updatedTicket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/assign/{userId}")
    public ResponseEntity<Map<String, String>> assignTicket(
            @PathVariable UUID id,
            @PathVariable UUID userId) {

        ticketService.assignTicket(id, userId);
        return ResponseEntity.ok(
                Map.of("message", "Ticket assigned successfully."));
    }

    @PatchMapping("/{id}/unassign/{userId}")
    public ResponseEntity<Map<String, String>> unassignTicket(@PathVariable UUID id, @PathVariable UUID userId) {

        ticketService.unassignTicket(id, userId);
        return ResponseEntity.ok(
                Map.of("message", "Ticket unassigned successfully."));

    }
}
