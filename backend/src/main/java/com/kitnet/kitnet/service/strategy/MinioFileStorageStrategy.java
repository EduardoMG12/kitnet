package com.kitnet.kitnet.service.strategy;

import com.kitnet.kitnet.exception.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Component
public class MinioFileStorageStrategy implements FileStorageStrategy {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public MinioFileStorageStrategy(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(MultipartFile file, String subdirectory) throws IOException {
        if (file.isEmpty()) {
            throw new FileUploadException("Cannot upload empty file.");
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        String keyName = subdirectory + "/" + uniqueFileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return String.format("http://localhost:80/%s/%s", bucketName, keyName);
    }


    @Override
    public void deleteFile(URI fileUri) throws IOException {
        String keyName = fileUri.getPath().substring(1);

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
}