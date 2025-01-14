package com.proiect.colectiv.server.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;


/**
 * Animal model
 * Represents an animal in the database with the following fields:
 * - id: UUID - unique identifier of the animal (primary key)
 * - name: String - name of the animal (e.g. Rex)
 * - description: String - description of the animal (e.g. breed, color)
 * - age: LocalDate - date of birth of the animal (if known)
 * - imageData: byte[] - image of the animal in byte format
 * - healthProfile: String - health profile of the animal (e.g. vaccinated, neutered)
 * - location: Location - location of the animal (foreign key)
 */
@Schema(description = "Animal entity representing a farm animal")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "animals", schema = "public")
public class Animal {

    @Schema(description = "Unique identifier of the animal", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Schema(description = "Name of the animal", example = "Rex", maxLength = 100)
    @Length(max = 100, message = "Animal name is too long")
    @NotEmpty(message = "Try again! Animal name cannot be empty")
    @Column(name = "name")
    private String name;

    @Schema(description = "Description of the animal (e.g., breed, color)", maxLength = 255, nullable = true)
    @Length(max = 255, message = "Description is too long")
    @Column(name = "description")
    private String description;

    @Schema(description = "Date of birth of the animal", nullable = true)
    @Column(name = "age")
    private LocalDate age;

    @Schema(description = "Image of the animal in byte format", nullable = true)
    @Column(name = "image_data")
    private byte[] imageData;

    @Schema(description = "Health profile of the animal (e.g., vaccinated, neutered)", maxLength = 255, nullable = true)
    @Length(max = 255, message = "Health profile is too long")
    @Column(name = "health_profile")
    private String healthProfile;

    @Schema(description = "Location where the animal is kept")
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
