package net.hackyourfuture.tickettrackingsystem.projects.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import net.hackyourfuture.tickettrackingsystem.projects.dto.ProjectCreateRequest;
import net.hackyourfuture.tickettrackingsystem.projects.model.ProjectModel;
import net.hackyourfuture.tickettrackingsystem.projects.response.ProjectSummaryResponse;
import net.hackyourfuture.tickettrackingsystem.projects.services.ProjectService;

@RestController
@RequestMapping("v1/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectModel>> getProjects() {
        List<ProjectModel> projects = projectService.getProjects();
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<ProjectModel> createProject(@Valid @RequestBody ProjectCreateRequest projectRequest) {
        ProjectModel project = projectService.createProject(projectRequest);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/summary")
    public ResponseEntity<List<ProjectSummaryResponse>> getProjectSummary() {
        List<ProjectSummaryResponse> summaries = projectService.getProjectSummary();
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectModel> getProjectById(@PathVariable UUID id) {
        ProjectModel project = projectService.getProjectById(id);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
