package com.lucentlearn.payas.app.StudentCRUD.repository;

import com.lucentlearn.payas.app.StudentCRUD.io.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
