package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.exception.FileUploadException;
import com.kitnet.kitnet.service.UploadService;
import com.kitnet.kitnet.service.strategy.FileStorageStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.net.URI;

@Service
public class UploadServiceImpl implements UploadService {

    private final FileStorageStrategy fileStorageStrategy;

    @Autowired
    public UploadServiceImpl(FileStorageStrategy fileStorageStrategy) {
        this.fileStorageStrategy = fileStorageStrategy;
    }

    @Override
    public String uploadFile(MultipartFile file, String subdirectory) throws IOException {
        return fileStorageStrategy.uploadFile(file, subdirectory);
    }

    @Override
    public void deleteFile(String fileUrl) throws IOException, FileUploadException {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return;
        }
        fileStorageStrategy.deleteFile(URI.create(fileUrl));
    }
}