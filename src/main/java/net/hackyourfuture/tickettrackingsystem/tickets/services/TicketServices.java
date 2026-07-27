package net.hackyourfuture.tickettrackingsystem.tickets.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import net.hackyourfuture.tickettrackingsystem.config.errorConfig.ResourceNotFoundException;
import net.hackyourfuture.tickettrackingsystem.enums.StatusEnum;
import net.hackyourfuture.tickettrackingsystem.projects.dao.ProjectDao;
import net.hackyourfuture.tickettrackingsystem.projects.model.ProjectModel;
import net.hackyourfuture.tickettrackingsystem.tickets.dao.TicketDao;
import net.hackyourfuture.tickettrackingsystem.tickets.dto.TicketCreateRequest;
import net.hackyourfuture.tickettrackingsystem.tickets.dto.TicketUpdateRequest;
import net.hackyourfuture.tickettrackingsystem.tickets.model.TicketModel;

@Service
public class TicketServices {
    private final TicketDao ticketDao;
    private final ProjectDao projectDao;

    public TicketServices(TicketDao ticketDao, ProjectDao projectDao) {
        this.ticketDao = ticketDao;
        this.projectDao = projectDao;
    }

    public TicketModel getTicketById(UUID id) {
        return ticketDao.getTicketById(id);
    }

    public TicketModel createTicket(TicketCreateRequest request) {
        ProjectModel project = projectDao.findById(request.projectId());
        if (project == null) {
            throw new ResourceNotFoundException("Project with id " + request.projectId() + " not found");
        }

        return ticketDao.createTicket(request.title(), request.description(), request.projectId());
    }

    public TicketModel updateTicket(UUID id, TicketUpdateRequest request) {
        TicketModel existingTicket = ticketDao.getTicketById(id);
        if (existingTicket == null) {
            throw new ResourceNotFoundException("Ticket with id " + id + " not found");
        }

        ProjectModel project = projectDao.findById(request.projectId());
        if (project == null) {
            throw new ResourceNotFoundException("Project with id " + request.projectId() + " not found");
        }

        StatusEnum status = StatusEnum.valueOf(request.status().toUpperCase());
        return ticketDao.updateTicket(id, request.title(), request.description(), request.projectId(), status);
    }

}
