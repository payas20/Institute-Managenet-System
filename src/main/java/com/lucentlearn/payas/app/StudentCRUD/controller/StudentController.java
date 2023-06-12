package com.lucentlearn.payas.app.StudentCRUD.controller;

import com.lucentlearn.payas.app.StudentCRUD.exceptions.StudentServiceException;
import com.lucentlearn.payas.app.StudentCRUD.exceptions.UserServiceException;
import com.lucentlearn.payas.app.StudentCRUD.io.entity.UserEntity;
import com.lucentlearn.payas.app.StudentCRUD.model.request.StudentDetailsRequestModel;
import com.lucentlearn.payas.app.StudentCRUD.model.request.UserLoginRequest;
import com.lucentlearn.payas.app.StudentCRUD.model.response.ErrorMessages;
import com.lucentlearn.payas.app.StudentCRUD.model.response.StudentRest;
import com.lucentlearn.payas.app.StudentCRUD.repository.UserRepository;
import com.lucentlearn.payas.app.StudentCRUD.service.StudentService;
import com.lucentlearn.payas.app.StudentCRUD.service.impl.JwtServiceImpl;
import com.lucentlearn.payas.app.StudentCRUD.shared.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.lucentlearn.payas.app.StudentCRUD.shared.dto.StudentDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    JwtServiceImpl jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<StudentRest> getStudent(@PathVariable String username, HttpServletRequest request){

        String user = utils.getUserName(request);
        if(!user.equals(username)) throw new StudentServiceException(ErrorMessages.PERMISSION_NOT_GRANTED.getErrorMessage());

        StudentDto storedStudent = studentService.getStudent(username);
        StudentRest reponseBody = new StudentRest();
        BeanUtils.copyProperties(storedStudent, reponseBody);

        return ResponseEntity.ok()
                .body(reponseBody);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<StudentRest> createStudent(@RequestBody StudentDetailsRequestModel student){

        StudentDto studentDto = new StudentDto();
        BeanUtils.copyProperties(student, studentDto);

        studentDto = studentService.createStudent(studentDto);

        StudentRest returnValue = new StudentRest();
        BeanUtils.copyProperties(studentDto, returnValue);

        return ResponseEntity.ok()
                .body(returnValue);
    }

    @PostMapping("/login")
    public ResponseEntity<String> studentLogin(@RequestBody UserLoginRequest user){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(), user.getPassword()
                )
        );

        HttpHeaders responseHeader = new HttpHeaders();
        String result="";

        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        if(userEntity == null ) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        if(userEntity!=null && !userEntity.getRole().equals("ROLE_STUDENT")){
            throw new StudentServiceException(ErrorMessages.PERMISSION_NOT_GRANTED.getErrorMessage());
        }

        UserEntity userDetails = new UserEntity();
        BeanUtils.copyProperties(user, userDetails);

        var jwtToken = jwtService.generateToken(userDetails);
        StringBuilder jwt = new StringBuilder("Bearer ");
        jwt.append(jwtToken);
        responseHeader.set("Authorization", new String(jwt));
        result = "Login Successful";

        return ResponseEntity.ok()
                .headers(responseHeader)
                .body(result);


    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public String updateStudent(){
        return "updateStudent";
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteStudent(@PathVariable String username, HttpServletRequest request){

        studentService.deleteStudent(username);

        UserEntity userToDelete = userRepository.findByEmail(username);
        if(userToDelete==null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userRepository.delete(userToDelete);

        return "Student Deleted Successfully";
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<StudentRest> getAllStudents(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "25") int limit){

        List<StudentRest> students = new ArrayList<>();

        List<StudentDto> allStudents = studentService.getAllStudents(page,limit);

        for (StudentDto student :
                allStudents) {
            StudentRest studentRest = new StudentRest();
            BeanUtils.copyProperties(student, studentRest);
            students.add(studentRest);
        }
        return students;
    }

}
