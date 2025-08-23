package com.kitnet.kitnet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.logging.Logger;

@Component
public class S3BucketInitializer implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(S3BucketInitializer.class.getName());

    private final S3Client s3Client;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Autowired
    public S3BucketInitializer(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Verificando e configurando o bucket S3: " + bucketName);
        try {
            boolean bucketExists = s3Client.listBuckets().buckets().stream()
                .anyMatch(bucket -> bucket.name().equals(bucketName));

            if (!bucketExists) {
                logger.info("Bucket '" + bucketName + "' não encontrado. Criando...");
                s3Client.createBucket(b -> b.bucket(bucketName));
                logger.info("Bucket '" + bucketName + "' create with success.");
            }
            
            configureBucketPolicy();

        } catch (S3Exception e) {
            logger.severe("Error to begin bucket: " + e.awsErrorDetails().errorMessage());
            throw new RuntimeException("Fail to begin MinIO", e);
        }
    }

    private void configureBucketPolicy() {
        String publicPolicy = String.format("""
            {
              "Version": "2012-10-17",
              "Statement": [
                {
                  "Effect": "Allow",
                  "Principal": "*",
                  "Action": [
                    "s3:GetObject"
                  ],
                  "Resource": [
                    "arn:aws:s3:::%s/users/*",
                    "arn:aws:s3:::%s/properties/*"
                  ]
                }
              ]
            }
            """, bucketName, bucketName);
        
        PutBucketPolicyRequest putBucketPolicyRequest = PutBucketPolicyRequest.builder()
            .bucket(bucketName)
            .policy(publicPolicy)
            .build();
        
        try {
            s3Client.putBucketPolicy(putBucketPolicyRequest);
            logger.info("Access granular policy is configured for bucket '" + bucketName + "'.");
        } catch (S3Exception e) {
            logger.severe("Error to configure policy of bucket: " + e.awsErrorDetails().errorMessage());
        }
    }
}