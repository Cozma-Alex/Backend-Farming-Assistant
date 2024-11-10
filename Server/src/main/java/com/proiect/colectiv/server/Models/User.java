package com.proiect.colectiv.server.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Email(message = "This is not a valid email")
    @NotEmpty(message = "Try again! Email cannot be empty")
    @Column(name = "email", unique = true)
    private String email;

    @Length(max = 255, message = "Password is too long")
    @NotEmpty(message = "Try again! Password cannot be empty")
    @Column(name = "password_hash")
    private String password_hash;


}
