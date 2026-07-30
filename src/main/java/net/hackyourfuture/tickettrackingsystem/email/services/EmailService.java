package net.hackyourfuture.tickettrackingsystem.email.services;

import java.util.List;

import org.springframework.stereotype.Service;

import net.hackyourfuture.tickettrackingsystem.email.client.ResendClient;
import net.hackyourfuture.tickettrackingsystem.email.dto.EmailRequest;
import net.hackyourfuture.tickettrackingsystem.tickets.model.TicketModel;
import net.hackyourfuture.tickettrackingsystem.users.model.UserModel;

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
                        List<UserModel> assignees) {

                List<String> emails = assignees.stream()
                                .map(UserModel::getEmail)
                                .toList();

                if (emails.isEmpty()) {
                        return;
                }

                resendClient.sendEmail(
                                new EmailRequest(
                                                emails,
                                                "Ticket Updated: " + ticket.getTitle(),
                                                templateService.ticketUpdated(ticket)));
        }

        public void sendAssigned(
                        TicketModel ticket,
                        UserModel user) {

                resendClient.sendEmail(
                                new EmailRequest(
                                                List.of(user.getEmail()),
                                                "Ticket Assigned",
                                                templateService.assigned(ticket)));
        }

        public void sendUnassigned(
                        TicketModel ticket,
                        UserModel user) {

                resendClient.sendEmail(
                                new EmailRequest(
                                                List.of(user.getEmail()),
                                                "Ticket Unassigned",
                                                templateService.unassigned(ticket)));
        }

}