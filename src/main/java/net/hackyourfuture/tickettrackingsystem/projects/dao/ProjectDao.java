package net.hackyourfuture.tickettrackingsystem.projects.dao;

import java.util.List;
import java.util.UUID;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import net.hackyourfuture.tickettrackingsystem.projects.model.ProjectModel;

@RegisterBeanMapper(ProjectModel.class)
public interface ProjectDao {

    @SqlQuery("""
                SELECT *
                FROM projects
                WHERE project_id = :id
            """)
    ProjectModel findById(UUID id);

    @SqlQuery("""
            SELECT *
            FROM projects
                """)

    List<ProjectModel> findAllProjects();

    @SqlQuery("""
                INSERT INTO projects (name)
                VALUES (:name)
                RETURNING project_id, name
            """)
    ProjectModel createProject(String name);

}
