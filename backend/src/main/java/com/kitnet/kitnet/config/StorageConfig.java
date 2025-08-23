package com.kitnet.kitnet.config;

import com.kitnet.kitnet.service.strategy.FileStorageStrategy;
import com.kitnet.kitnet.service.strategy.MinioFileStorageStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class StorageConfig {

    @Bean
    @Profile({"dev", "prod"})
    public FileStorageStrategy fileStorageStrategy(S3Client s3Client) {
        return new MinioFileStorageStrategy(s3Client);
    }
}