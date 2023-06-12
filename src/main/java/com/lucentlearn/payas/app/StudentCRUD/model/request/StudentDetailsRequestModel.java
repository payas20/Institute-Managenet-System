package com.lucentlearn.payas.app.StudentCRUD.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetailsRequestModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String degree;
    private String course;
    private int semester;
}
