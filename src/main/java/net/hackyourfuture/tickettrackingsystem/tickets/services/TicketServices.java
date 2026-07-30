package net.hackyourfuture.tickettrackingsystem.tickets.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import net.hackyourfuture.tickettrackingsystem.config.errorConfig.ResourceNotFoundException;
import net.hackyourfuture.tickettrackingsystem.email.services.EmailService;
import net.hackyourfuture.tickettrackingsystem.enums.StatusEnum;
import net.hackyourfuture.tickettrackingsystem.projects.dao.ProjectDao;
import net.hackyourfuture.tickettrackingsystem.projects.model.ProjectModel;
import net.hackyourfuture.tickettrackingsystem.tickets.dao.TicketAssignmentDao;
import net.hackyourfuture.tickettrackingsystem.tickets.dao.TicketDao;
import net.hackyourfuture.tickettrackingsystem.tickets.dto.TicketCreateRequest;
import net.hackyourfuture.tickettrackingsystem.tickets.dto.TicketSearchRequest;
import net.hackyourfuture.tickettrackingsystem.tickets.dto.TicketUpdateRequest;
import net.hackyourfuture.tickettrackingsystem.tickets.model.TicketModel;
import net.hackyourfuture.tickettrackingsystem.users.dao.UserDao;
import net.hackyourfuture.tickettrackingsystem.users.model.UserModel;

@Service
public class TicketServices {
    private final TicketDao ticketDao;
    private final ProjectDao projectDao;
    private final TicketAssignmentDao ticketAssignmentDao;
    private final UserDao userDao;
    private final EmailService emailService;

    public TicketServices(TicketDao ticketDao, ProjectDao projectDao, TicketAssignmentDao ticketAssignmentDao,
            UserDao userDao, EmailService emailService) {
        this.ticketDao = ticketDao;
        this.projectDao = projectDao;
        this.ticketAssignmentDao = ticketAssignmentDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

    public TicketModel getTicketById(UUID id) {
        return ticketDao.getTicketById(id);
    }

    public void checkProjectExists(UUID projectId) {
        ProjectModel project = projectDao.findById(projectId);
        if (project == null) {
            throw new ResourceNotFoundException("Project with id " + projectId + " not found");
        }
    }

    public void checkTicketExists(UUID ticketId) {
        TicketModel ticket = ticketDao.getTicketById(ticketId);
        if (ticket == null) {
            throw new ResourceNotFoundException("Ticket with id " + ticketId + " not found");
        }
    }

    public TicketModel createTicket(TicketCreateRequest request) {
        checkProjectExists(request.projectId());

        return ticketDao.createTicket(request.title(), request.description(), request.projectId());
    }

    @Transactional
    public TicketModel updateTicket(UUID id, TicketUpdateRequest request) {
        checkTicketExists(id);

        checkProjectExists(request.projectId());

        StatusEnum status = StatusEnum.valueOf(request.status().toUpperCase());

        TicketModel updatedTicket = ticketDao.updateTicket(id, request.title(), request.description(),
                request.projectId(), status.getStatus());

        List<UserModel> assignees = getAssignees(id);

        try {
            emailService.sendTicketUpdated(updatedTicket, assignees);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email notification: " + e.getMessage(), e);
        }

        return updatedTicket;
    }

    private void validateAssignment(UUID ticketId, UUID userId) {
        checkTicketExists(ticketId);

        if (userDao.findById(userId) == null) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Transactional
    public void assignTicket(UUID ticketId, UUID userId) {
        validateAssignment(ticketId, userId);

        ticketAssignmentDao.assignUser(ticketId, userId);
        ticketDao.updateTicketTimestamp(ticketId);

        TicketModel ticket = ticketDao.getTicketById(ticketId);

        UserModel user = userDao.findById(userId);

        try {
            emailService.sendAssigned(ticket, user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email notification: " + e.getMessage(), e);
        }

    }

    @Transactional
    public void unassignTicket(UUID ticketId, UUID userId) {
        validateAssignment(ticketId, userId);

        ticketAssignmentDao.unassignUser(ticketId, userId);
        ticketDao.updateTicketTimestamp(ticketId);

        TicketModel ticket = ticketDao.getTicketById(ticketId);

        UserModel user = userDao.findById(userId);

        try {
            emailService.sendUnassigned(ticket, user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email notification: " + e.getMessage(), e);
        }
    }

    public List<UserModel> getAssignees(UUID ticketId) {
        checkTicketExists(ticketId);

        return ticketAssignmentDao.getAssignees(ticketId);
    }

    public List<TicketModel> searchTickets(TicketSearchRequest request) {

        String text = null;

        if (request.text() != null && !request.text().isBlank()) {
            text = "%" + request.text().toLowerCase() + "%";
        }

        String status = null;

        if (request.status() != null) {
            status = request.status().name().toLowerCase();
        }

        return ticketDao.searchTickets(text, status);
    }

}
