package net.hackyourfuture.tickettrackingsystem.tickets.dao;

import java.util.List;
import java.util.UUID;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import net.hackyourfuture.tickettrackingsystem.users.model.UserModel;

public interface TicketAssignmentDao {
    @SqlQuery("""
            SELECT u.*
            FROM users u
            JOIN ticket_assignment ta
              ON ta.user_id = u.user_id
            WHERE ta.ticket_id = :ticketId
            """)
    @RegisterBeanMapper(UserModel.class)
    List<UserModel> getAssignees(UUID ticketId);

    @SqlUpdate("""
            INSERT INTO ticket_assignment(ticket_id, user_id)
            VALUES (:ticketId, :userId)
            """)
    void assignUser(UUID ticketId, UUID userId);

    @SqlUpdate("""
            DELETE FROM ticket_assignment
            WHERE ticket_id = :ticketId AND user_id = :userId
            """)
    void unassignUser(UUID ticketId, UUID userId);

    
}
