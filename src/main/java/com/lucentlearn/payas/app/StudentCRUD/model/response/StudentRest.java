package com.lucentlearn.payas.app.StudentCRUD.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRest {
    private String email;
    private String course;
    private int semester;
}
