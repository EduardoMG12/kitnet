package com.kitnet.kitnet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import java.net.URI;

@Configuration
public class S3Config {

    @Value("${aws.s3.endpoint.url}")
    private String basepath_bucket;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of("us-east-1"))
                .endpointOverride(URI.create(basepath_bucket))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("rootuser", "rootpassword")))
                .forcePathStyle(true)
                .build();
    }
}
