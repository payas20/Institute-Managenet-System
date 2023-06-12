package com.lucentlearn.payas.app.StudentCRUD.exceptions;

import com.lucentlearn.payas.app.StudentCRUD.model.response.ErrorMessageRest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = {AdminServiceException.class})
    public ResponseEntity<Object> handleAdminServiceException(AdminServiceException ex, WebRequest request){

        ErrorMessageRest err = new ErrorMessageRest(new Date(), ex.getMessage());

        return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {StudentServiceException.class})
    public ResponseEntity<Object> handleStudentServiceException(StudentServiceException ex, WebRequest request){

        ErrorMessageRest err = new ErrorMessageRest(new Date(), ex.getMessage());

        return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request){

        ErrorMessageRest err = new ErrorMessageRest(new Date(), ex.getMessage());

        return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request){

        ErrorMessageRest err = new ErrorMessageRest(new Date(), ex.getMessage());

        return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
