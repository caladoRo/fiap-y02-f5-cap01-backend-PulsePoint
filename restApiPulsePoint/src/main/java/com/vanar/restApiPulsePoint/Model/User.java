package com.vanar.restApiPulsePoint.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "User")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String uuid;

    @Column(name = "uuid_expire", length = 255)
    private String uuidExpire;

    @NotBlank
    @Email
    @Column(nullable = false, length = 255, unique = true)
    private String email;

    // Não retornar em respostas
    @NotBlank
    @JsonIgnore
    @Column(nullable = false, length = 255)
    private String password;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String name;

    // Mantém como string; valida apenas os 3 valores possíveis
    @NotBlank
    @Pattern(regexp = "admin|paciente|medico", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "role deve ser admin, paciente ou medico")
    @Column(nullable = false, length = 50)
    private String role;

    // Deixar o Hibernate preencher na criação (ou o default do banco)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "DATETIME")
    private Timestamp createdAt;

    // tinyint(1) ↔ boolean
    @Column(columnDefinition = "TINYINT(1)")
    private boolean active;
}
