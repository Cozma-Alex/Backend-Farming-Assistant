package com.proiect.colectiv.server.Models;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "User entity representing a farm management system user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users", schema = "public")
public class User {

    @Schema(description = "Unique identifier of the user", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Schema(description = "User's email address", example = "user@example.com")
    @Email(message = "This is not a valid email")
    @NotEmpty(message = "Try again! Email cannot be empty")
    @Column(name = "email", unique = true)
    private String email;

    @Schema(description = "User's password (will be hashed)", writeOnly = true)
    @Length(max = 255, message = "Password is too long")
    @NotEmpty(message = "Try again! Password cannot be empty")
    @Column(name = "password_hash")
    private String passwordHash;

    @Schema(description = "Name of the user's farm", example = "Green Valley Farm")
    @Length(max = 100, message = "Farm name is too long")
    @NotEmpty(message = "Try again! Farm name cannot be empty")
    @Column(name = "farm_name")
    private String farmName;

    @Schema(description = "Name of the user", example = "John Doe")
    @Length(max = 100, message = "Name is too long")
    @NotEmpty(message = "Try again! Name cannot be empty")
    @Column(name = "name")
    private String name;

    @Schema(description = "User's profile image in byte array format", nullable = true)
    @Column(name = "image_data")
    private byte[] imageData;


}
