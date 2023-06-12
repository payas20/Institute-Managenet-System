package com.lucentlearn.payas.app.StudentCRUD.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageRest {
    private Date timestamp;
    private String message;
}
