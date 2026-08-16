package net.hackyourfuture.tickettrackingsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
public class B2Config {

    @Bean
    public S3Client s3Client(
            @Value("${b2.endpoint}") String endpoint,
            @Value("${b2.region}") String region,
            @Value("${b2.access-key}") String accessKey,
            @Value("${b2.secret-key}") String secretKey) {

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials)
                )
                .build();
    }

     @Bean
    public S3Presigner s3Presigner(
            @Value("${b2.endpoint}") String endpoint,
            @Value("${b2.region}") String region,
            @Value("${b2.access-key}") String accessKey,
            @Value("${b2.secret-key}") String secretKey) {

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(accessKey, secretKey);

        return S3Presigner.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials)
                )
                .build();
    }
}