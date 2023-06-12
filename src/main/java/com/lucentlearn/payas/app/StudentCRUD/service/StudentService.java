package com.lucentlearn.payas.app.StudentCRUD.service;

import com.lucentlearn.payas.app.StudentCRUD.shared.dto.StudentDto;

import java.util.List;

public interface StudentService {
    StudentDto createStudent(StudentDto studentDto);

    StudentDto getStudent(String username);

    void deleteStudent(String username);

    List<StudentDto> getAllStudents(int page, int limit);
}
