package net.hackyourfuture.tickettrackingsystem.tickets.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.hackyourfuture.tickettrackingsystem.enums.StatusEnum;
import net.hackyourfuture.tickettrackingsystem.users.model.UserModel;

@Getter
@Setter
@NoArgsConstructor
public class TicketModel {
    private UUID ticketId;

    private String title;

    private String description;

    private UUID projectId;

    private StatusEnum status;

    private Date createdAt;

    private Date updatedAt;

    private List<UserModel> assignees = new ArrayList<>();

}
