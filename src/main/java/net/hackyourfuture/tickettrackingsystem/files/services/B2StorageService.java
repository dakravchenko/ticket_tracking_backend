package net.hackyourfuture.tickettrackingsystem.files.services;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class B2StorageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    private final String bucket;

    private final long expirationMinutes;

    public B2StorageService(
            S3Client s3Client,
            S3Presigner s3Presigner,
            @Value("${b2.bucket}") String bucket,
            @Value("${b2.presigned-url-expiration-minutes}") long expirationMinutes) {

        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.bucket = bucket;
        this.expirationMinutes = expirationMinutes;
    }

    public String generateUploadUrl(
            String storageKey,
            String contentType) {

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(storageKey)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(
                        Duration.ofMinutes(
                                expirationMinutes))
                .putObjectRequest(request)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(
                presignRequest);

        return presignedRequest.url().toString();
    }

    public String generateDownloadUrl(
            String storageKey) {

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(storageKey)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(
                        Duration.ofMinutes(
                                expirationMinutes))
                .getObjectRequest(request)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(
                presignRequest);

        return presignedRequest.url().toString();
    }

    public HeadObjectResponse getMetadata(
            String storageKey) {

        HeadObjectRequest request = HeadObjectRequest.builder()
                .bucket(bucket)
                .key(storageKey)
                .build();

        return s3Client.headObject(request);
    }

    public void delete(String storageKey) {

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(storageKey)
                .build();

        s3Client.deleteObject(request);
    }
}