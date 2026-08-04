package net.hackyourfuture.tickettrackingsystem.projects.model;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectModel {
    private UUID projectId;
    private String name;
}
