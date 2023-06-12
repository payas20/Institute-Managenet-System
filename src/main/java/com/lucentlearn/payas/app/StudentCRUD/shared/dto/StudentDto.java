package com.lucentlearn.payas.app.StudentCRUD.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String degree;
    private String course;
    private int semester;
    private final String role = "ROLE_STUDENT";
}
