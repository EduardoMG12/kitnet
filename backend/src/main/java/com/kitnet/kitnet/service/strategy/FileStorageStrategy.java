package com.kitnet.kitnet.service.strategy;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URI;

public interface FileStorageStrategy {
    String uploadFile(MultipartFile file, String subdirectory) throws IOException;
    void deleteFile(URI fileUri) throws IOException;
}