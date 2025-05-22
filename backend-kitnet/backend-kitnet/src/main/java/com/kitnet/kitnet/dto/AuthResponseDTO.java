package com.kitnet.kitnet.dto;

import com.kitnet.kitnet.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private User user;
    private String jwtToken;
}
