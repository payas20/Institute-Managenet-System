package com.lucentlearn.payas.app.StudentCRUD.repository;

import com.lucentlearn.payas.app.StudentCRUD.io.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    AdminEntity findByEmail(String email);
}
