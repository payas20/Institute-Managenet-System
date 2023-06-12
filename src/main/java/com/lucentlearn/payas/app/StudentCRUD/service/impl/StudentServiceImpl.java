package com.lucentlearn.payas.app.StudentCRUD.service.impl;

import com.lucentlearn.payas.app.StudentCRUD.exceptions.StudentServiceException;
import com.lucentlearn.payas.app.StudentCRUD.io.entity.StudentEntity;
import com.lucentlearn.payas.app.StudentCRUD.io.entity.UserEntity;
import com.lucentlearn.payas.app.StudentCRUD.model.response.ErrorMessages;
import com.lucentlearn.payas.app.StudentCRUD.repository.StudentRepository;
import com.lucentlearn.payas.app.StudentCRUD.repository.UserRepository;
import com.lucentlearn.payas.app.StudentCRUD.service.StudentService;
import com.lucentlearn.payas.app.StudentCRUD.shared.dto.StudentDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {

        StudentEntity student = studentRepository.findByEmail(studentDto.getEmail());

        if(student!=null) throw new StudentServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

        studentDto.setEncryptedPassword(passwordEncoder.encode(studentDto.getPassword()));

        StudentEntity studentEntity = new StudentEntity();
        BeanUtils.copyProperties(studentDto, studentEntity);

        StudentEntity createdStudent = studentRepository.save(studentEntity);

        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(studentDto, user);

        userRepository.save(user);

        StudentDto savedStudent = new StudentDto();
        BeanUtils.copyProperties(studentEntity, savedStudent);

        return savedStudent;
    }

    @Override
    public StudentDto getStudent(String username) {

        StudentEntity student = studentRepository.findByEmail(username);

        if(student==null) throw new StudentServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        StudentDto returnValue = new StudentDto();
        BeanUtils.copyProperties(student, returnValue);

        return returnValue;
    }

    @Override
    public void deleteStudent(String username) {
        StudentEntity student = studentRepository.findByEmail(username);

        if(student==null) throw new StudentServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        studentRepository.delete(student);
    }

    @Override
    public List<StudentDto> getAllStudents(int page, int limit) {

        List<StudentDto> returnValue = new ArrayList<>();

        if(page>0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<StudentEntity> students = studentRepository.findAll(pageableRequest);

        for (StudentEntity student :
                students) {
            StudentDto studentDto = new StudentDto();
            BeanUtils.copyProperties(student, studentDto);
            returnValue.add(studentDto);
        }

        return returnValue;
    }
}
