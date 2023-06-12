package com.lucentlearn.payas.app.StudentCRUD.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private final String role = "ROLE_ADMIN";
}
