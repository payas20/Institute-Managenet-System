package com.lucentlearn.payas.app.StudentCRUD.io.entity;

import com.lucentlearn.payas.app.StudentCRUD.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
@EntityListeners(AuditingEntityListener.class)
public class StudentEntity extends Auditable<String> {
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

    @Column(length = 30, nullable = false)
    private String degree;

    @Column(length = 100, nullable = false)
    private String course;

    @Column(nullable = false)
    private int semester;
}
