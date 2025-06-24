package com.kitnet.kitnet.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfilePictureUploadResponseDTO {
    private String message;
    private String imageUrl;
    private String userId;
}