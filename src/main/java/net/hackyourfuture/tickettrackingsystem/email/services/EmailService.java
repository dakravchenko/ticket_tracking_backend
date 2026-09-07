package net.hackyourfuture.tickettrackingsystem.email.services;

import java.util.List;

import org.springframework.stereotype.Service;

import net.hackyourfuture.tickettrackingsystem.email.client.ResendClient;
import net.hackyourfuture.tickettrackingsystem.email.dto.EmailRequest;
import net.hackyourfuture.tickettrackingsystem.tickets.model.TicketModel;
import net.hackyourfuture.tickettrackingsystem.users.dto.UserResponse;

@Service
public class EmailService {

        private final ResendClient resendClient;
        private final EmailTemplateService templateService;

        public EmailService(
                        ResendClient resendClient,
                        EmailTemplateService templateService) {

                this.resendClient = resendClient;
                this.templateService = templateService;
        }

        public void sendTicketUpdated(
                        TicketModel ticket,
                        List<UserResponse> assignees) {

                List<String> emails = assignees.stream()
                                .map(UserResponse::email)
                                .toList();

                if (emails.isEmpty()) {
                        return;
                }

                System.out.println(emails);

                for (String email : emails) {
                        resendClient.sendEmail(
                                        new EmailRequest(
                                                        email,
                                                        "Ticket Updated: " + ticket.getTitle(),
                                                        templateService.ticketUpdated(ticket)));
                }

        }

        public void sendAssigned(
                        TicketModel ticket,
                        UserResponse user) {

                resendClient.sendEmail(
                                new EmailRequest(
                                                user.email(),
                                                "Ticket Assigned",
                                                templateService.assigned(ticket)));
        }

        public void sendUnassigned(
                        TicketModel ticket,
                        UserResponse user) {

                resendClient.sendEmail(
                                new EmailRequest(
                                                user.email(),
                                                "Ticket Unassigned",
                                                templateService.unassigned(ticket)));
        }

}