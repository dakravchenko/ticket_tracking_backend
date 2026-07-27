package net.hackyourfuture.tickettrackingsystem.tickets.dao;

import java.util.UUID;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import net.hackyourfuture.tickettrackingsystem.tickets.model.TicketModel;

@RegisterBeanMapper(TicketModel.class)
public interface TicketDao {
    @SqlQuery("""
                SELECT *
                FROM tickets
                WHERE ticket_id = :id
            """)
    TicketModel getTicketById(@Bind("id") UUID id);

    @SqlQuery("""
                INSERT INTO tickets (title, description, project_id)
                VALUES (:title, :description, :projectId)
                RETURNING *
            """)
    TicketModel createTicket(@Bind("title") String title, @Bind("description") String description,
            @Bind("projectId") UUID projectId);

    @SqlQuery("""
                UPDATE tickets
                SET title = :title, description = :description, project_id = :projectId, status = CAST(:status AS status), updated_at = NOW()
                WHERE ticket_id = :id
                RETURNING *
            """)
    TicketModel updateTicket(@Bind("id") UUID id, @Bind("title") String title,
            @Bind("description") String description,
            @Bind("projectId") UUID projectId, @Bind("status") String status);

    @SqlUpdate("""
            UPDATE tickets
            SET updated_at = NOW()
            WHERE ticket_id = :id

                  """)

    void updateTicketTimestamp(@Bind("id") UUID id);

}
