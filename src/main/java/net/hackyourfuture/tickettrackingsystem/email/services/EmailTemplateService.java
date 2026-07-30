package net.hackyourfuture.tickettrackingsystem.email.services;

import org.springframework.stereotype.Service;

import net.hackyourfuture.tickettrackingsystem.tickets.model.TicketModel;

@Service
public class EmailTemplateService {

    public String ticketUpdated(TicketModel ticket) {

        return """
                <html>
                    <body>
                        <h2>Ticket Updated</h2>

                        <p>A ticket assigned to you has been updated.</p>

                        <table border="1" cellpadding="8">
                            <tr>
                                <td><b>Title</b></td>
                                <td>%s</td>
                            </tr>

                            <tr>
                                <td><b>Description</b></td>
                                <td>%s</td>
                            </tr>

                            <tr>
                                <td><b>Status</b></td>
                                <td>%s</td>
                            </tr>
                        </table>
                    </body>
                </html>
                """
                .formatted(
                        ticket.getTitle(),
                        ticket.getDescription(),
                        ticket.getStatus());
    }

    public String assigned(TicketModel ticket) {

        return """
                <h2>You have been assigned a ticket</h2>

                <p><b>%s</b></p>

                <p>%s</p>
                """
                .formatted(
                        ticket.getTitle(),
                        ticket.getDescription());
    }

    public String unassigned(TicketModel ticket) {

        return """
                <h2>You have been removed from a ticket</h2>

                <p>%s</p>
                """
                .formatted(ticket.getTitle());
    }

}