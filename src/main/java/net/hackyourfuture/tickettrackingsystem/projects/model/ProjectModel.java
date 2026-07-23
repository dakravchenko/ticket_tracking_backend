package net.hackyourfuture.tickettrackingsystem.projects.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class ProjectModel {

    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private UUID projectId;

    private String name;

}
