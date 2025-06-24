package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.exception.FileUploadException;
import com.kitnet.kitnet.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${app.upload.local-base-path:./cdn-temp/files}") // Base path on host or container volume
    private String uploadBasePath;

    @Value("${app.cdn.temp-url:http://localhost:8080}") // Base URL for the temporary CDN
    private String cdnTempUrl;

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

        Path uploadDir = Paths.get(uploadBasePath, subdirectory);

        Files.createDirectories(uploadDir);

        Path filePath = uploadDir.resolve(uniqueFileName);

        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new FileUploadException("Failed to save file: " + uniqueFileName, e);
        }

        return cdnTempUrl + "/" + subdirectory + "/" + uniqueFileName;
    }

    @Override
    public void deleteFile(String fileUrl) throws IOException, FileUploadException {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return;
        }

        URI uri = URI.create(fileUrl);
        String relativePath = uri.getPath();

        String pathRelativeToBase = relativePath;
        if (relativePath.startsWith("/")) {
            pathRelativeToBase = relativePath.substring(1);
        }

        Path filePathToDelete = Paths.get(uploadBasePath, pathRelativeToBase);

        if (Files.exists(filePathToDelete)) {
            try {
                Files.delete(filePathToDelete);
            } catch (IOException e) {
                throw new FileUploadException("Failed to delete file: " + fileUrl, e);
            }
        }
    }
}