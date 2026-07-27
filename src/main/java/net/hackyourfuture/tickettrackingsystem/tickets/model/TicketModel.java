package net.hackyourfuture.tickettrackingsystem.tickets.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.hackyourfuture.tickettrackingsystem.enums.StatusEnum;
import net.hackyourfuture.tickettrackingsystem.users.model.UserModel;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class TicketModel {
    @Id
    @GeneratedValue
    @Column(name = "ticket_id")
    private UUID ticketId;

    private String title;

    private String description;

    @Column(name = "project_id")
    private UUID projectId;

    private StatusEnum status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Transient
    private List<UserModel> assignees = new ArrayList<>();

}
