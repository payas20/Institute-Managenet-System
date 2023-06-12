package com.lucentlearn.payas.app.StudentCRUD.io.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admins")
public class AdminEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 30, nullable = false)
    private String firstName;

    @Column(length = 30, nullable = false)
    private String lastName;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;
}
