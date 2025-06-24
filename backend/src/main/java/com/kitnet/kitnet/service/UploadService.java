package com.kitnet.kitnet.service;

import com.kitnet.kitnet.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    // subdirectory: ex: "properties", "users/profile_pics", "user_documents/cpf"
    String uploadFile(MultipartFile file, String subdirectory) throws IOException;
    void deleteFile(String fileUrl) throws IOException, FileUploadException;
}
