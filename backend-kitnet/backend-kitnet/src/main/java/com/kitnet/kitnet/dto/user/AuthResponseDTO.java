package com.kitnet.kitnet.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private UserSimpleResponseDTO user;
    private String jwtToken;
}
