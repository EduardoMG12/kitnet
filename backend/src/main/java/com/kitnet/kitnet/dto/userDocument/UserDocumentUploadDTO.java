// src/main/java/com/kitnet.kitnet.dto.user/UserDocumentUploadDTO.java
package com.kitnet.kitnet.dto.userDocument;

import com.kitnet.kitnet.model.enums.DocumentType; // Importe
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile; // Importe


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDocumentUploadDTO {
    private DocumentType documentType;
    private MultipartFile file;
}