package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.upload.UserProfilePictureUploadResponseDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.enums.DocumentType;
import com.kitnet.kitnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    @Autowired
    private
    UserService userService;



//    @PostMapping("/documents/upload")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<List<Map<String, String>>> uploadUserDocuments(
//            @AuthenticationPrincipal User authenticatedUser,
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("types") List<DocumentType> documentTypes) throws Exception {
//
//    }
        //
}
