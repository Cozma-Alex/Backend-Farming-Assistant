package com.proiect.colectiv.server.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "animals", schema = "public")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Length(max = 100, message="Animal name is too long")
    @NotEmpty(message = "Try again! Animal name cannot be empty")
    @Column(name = "name")
    private String name;

    @Length(max = 255, message = "Description is too long")
    @Column(name = "description")
    private String description;

    @Column(name = "age")
    private LocalDate age;

    @Column(name="image_data")
    private byte[] image_data;

    @Length(max = 255, message = "Health profile is too long")
    @Column(name = "health_profile")
    private String health_profile;


    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

}
