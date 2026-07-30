package net.hackyourfuture.tickettrackingsystem.email.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import net.hackyourfuture.tickettrackingsystem.email.dto.EmailRequest;
import net.hackyourfuture.tickettrackingsystem.email.dto.ResendRequest;
import net.hackyourfuture.tickettrackingsystem.email.dto.ResendResponse;

@Component
public class ResendClient {

        private final RestClient restClient;

        @Value("${resend.apiKey}")
        private String apiKey;

        @Value("${resend.from}")
        private String from;

        public ResendClient(
                        @Value("${resend.baseUrl}") String baseUrl) {

                this.restClient = RestClient.builder()
                                .baseUrl(baseUrl)
                                .build();
        }

        public void sendEmail(EmailRequest request) {

                ResendRequest resendRequest = new ResendRequest(
                                from,
                                request.recipients(),
                                request.subject(),
                                request.html());

                restClient.post()
                                .uri("/emails")
                                .header("Authorization", "Bearer " + apiKey)
                                .body(resendRequest)
                                .retrieve()
                                .toEntity(ResendResponse.class);
        }
}