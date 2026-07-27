package net.hackyourfuture.tickettrackingsystem.tickets.services;

import java.util.List;
import java.util.UUID;

import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import net.hackyourfuture.tickettrackingsystem.config.errorConfig.DuplicateAssignmentException;
import net.hackyourfuture.tickettrackingsystem.config.errorConfig.ResourceNotFoundException;
import net.hackyourfuture.tickettrackingsystem.enums.StatusEnum;
import net.hackyourfuture.tickettrackingsystem.projects.dao.ProjectDao;
import net.hackyourfuture.tickettrackingsystem.projects.model.ProjectModel;
import net.hackyourfuture.tickettrackingsystem.tickets.dao.TicketAssignmentDao;
import net.hackyourfuture.tickettrackingsystem.tickets.dao.TicketDao;
import net.hackyourfuture.tickettrackingsystem.tickets.dto.TicketCreateRequest;
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

    public TicketServices(TicketDao ticketDao, ProjectDao projectDao, TicketAssignmentDao ticketAssignmentDao,
            UserDao userDao) {
        this.ticketDao = ticketDao;
        this.projectDao = projectDao;
        this.ticketAssignmentDao = ticketAssignmentDao;
        this.userDao = userDao;
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

    public TicketModel updateTicket(UUID id, TicketUpdateRequest request) {
        checkTicketExists(id);

        checkProjectExists(request.projectId());

        StatusEnum status = StatusEnum.valueOf(request.status().toUpperCase());

        return ticketDao.updateTicket(id, request.title(), request.description(), request.projectId(),
                status.getStatus());
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

    }

    @Transactional
    public void unassignTicket(UUID ticketId, UUID userId) {
        validateAssignment(ticketId, userId);

        ticketAssignmentDao.unassignUser(ticketId, userId);
        ticketDao.updateTicketTimestamp(ticketId);
    }

    public List<UserModel> getAssignees(UUID ticketId) {
        checkTicketExists(ticketId);

        return ticketAssignmentDao.getAssignees(ticketId);
    }

}
