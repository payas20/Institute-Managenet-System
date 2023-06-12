package com.lucentlearn.payas.app.StudentCRUD.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRest {
    private String firstName;
    private String lastName;
    private String role;
}
