package net.hackyourfuture.tickettrackingsystem.projects.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import net.hackyourfuture.tickettrackingsystem.projects.dao.ProjectDao;
import net.hackyourfuture.tickettrackingsystem.projects.dto.ProjectCreateRequest;
import net.hackyourfuture.tickettrackingsystem.projects.model.ProjectModel;
import net.hackyourfuture.tickettrackingsystem.projects.response.ProjectSummaryResponse;

@Service
public class ProjectService {
    private final ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public List<ProjectModel> getProjects() {
        return projectDao.findAllProjects();
    }

    public ProjectModel getProjectById(UUID id) {
        return projectDao.findById(id);
    }

    public ProjectModel createProject(ProjectCreateRequest projectRequest) {
        return projectDao.createProject(projectRequest.name());
    }

    public List<ProjectSummaryResponse> getProjectSummary() {
        List<ProjectSummaryResponse> summaries = projectDao.getProjectsSummary();
        return summaries;
    }
}
