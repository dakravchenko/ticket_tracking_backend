package net.hackyourfuture.tickettrackingsystem.projects.dao;

import java.util.List;
import java.util.UUID;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import net.hackyourfuture.tickettrackingsystem.projects.model.ProjectModel;
import net.hackyourfuture.tickettrackingsystem.projects.response.ProjectSummaryResponse;

public interface ProjectDao {
    @RegisterBeanMapper(ProjectModel.class)
    @SqlQuery("""
                SELECT *
                FROM projects
                WHERE project_id = :id
            """)
    ProjectModel findById(UUID id);

    @RegisterBeanMapper(ProjectModel.class)
    @SqlQuery("""
            SELECT *
            FROM projects
                """)

    List<ProjectModel> findAllProjects();

    @RegisterBeanMapper(ProjectModel.class)
    @SqlQuery("""
                INSERT INTO projects (name)
                VALUES (:name)
                RETURNING project_id, name
            """)
    ProjectModel createProject(String name);

    @RegisterConstructorMapper(ProjectSummaryResponse.class)
    @SqlQuery("""
                SELECT p.name AS projectName,
                       COUNT(t.ticket_id) AS totalTickets,
                       COUNT(CASE WHEN t.status = 'open' THEN 1 END) AS openTickets,
                       COUNT(CASE WHEN t.status = 'in_progress' THEN 1 END) AS inProgressTickets,
                       COUNT(CASE WHEN t.status = 'closed' THEN 1 END) AS closedTickets
                FROM projects p
                LEFT JOIN tickets t ON p.project_id = t.project_id
                GROUP BY p.project_id, p.name
            """)

    List<ProjectSummaryResponse> getProjectsSummary();

}
