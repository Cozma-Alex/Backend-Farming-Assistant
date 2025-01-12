package com.proiect.colectiv.server.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

/**
 * User model
 * This class is used to create a user object with the following attributes:
 * - id: UUID - the id of the user (primary key)
 * - email: String - the email of the user
 * - passwordHash: String - the password hash of the user's password
 * - farmName: String - the name of the farm
 * - name: String - the name of the user
 * - imageData: byte[] - the image data of the user
 */
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
    private String passwordHash;

    @Length(max = 100, message = "Farm name is too long")
    @NotEmpty(message = "Try again! Farm name cannot be empty")
    @Column(name = "farm_name")
    private String farmName;

    @Length(max = 100, message = "Name is too long")
    @NotEmpty(message = "Try again! Name cannot be empty")
    @Column(name = "name")
    private String name;

    @Column(name = "image_data")
    private byte[] imageData;


}
