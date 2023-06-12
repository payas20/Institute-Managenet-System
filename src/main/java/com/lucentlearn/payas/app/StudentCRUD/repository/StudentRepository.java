package com.lucentlearn.payas.app.StudentCRUD.repository;

import com.lucentlearn.payas.app.StudentCRUD.io.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> , PagingAndSortingRepository<StudentEntity, Long> {
    StudentEntity findByEmail(String email);
}
