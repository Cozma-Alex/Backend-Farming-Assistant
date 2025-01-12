package com.proiect.colectiv.server.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Vehicle model
 * Represents a vehicle in the database with the following fields:
 * - id: UUID - the unique identifier of the vehicle (primary key)
 * - name: String - the name of the vehicle (not null, max 150 characters)
 * - description: String - the description of the vehicle (max 255 characters)
 * - model: String - the model of the vehicle (max 100 characters)
 * - brand: String - the brand of the vehicle (max 100 characters)
 * - year: int - the year of the vehicle
 * - price: double - the price of the vehicle
 * - kilometers: double - the number of kilometers of the vehicle
 * - acquisitionDate: LocalDate - the date when the vehicle was acquired
 * - user: User - the user that owns the vehicle
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "vehicles", schema = "public")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Length(max = 150, message = "Food name is too long")
    @NotEmpty(message = "Try again! Food name cannot be empty")
    @Column(name = "name")
    private String name;

    @Length(max = 255, message = "Description is too long")
    @Column(name = "description")
    private String description;

    @Length(max = 100, message = "Vehicle model is too long")
    @Column(name = "model")
    private String model;

    @Length(max = 100, message = "Vehicle brand is too long")
    @Column(name = "brand")
    private String brand;

    @Column(name = "year")
    private int year;

    @Column(name = "price")
    private double price;

    @Column(name = "kilometers")
    private double kilometers;

    @Column(name = "acquisition_date")
    private LocalDate acquisitionDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
