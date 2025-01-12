package com.proiect.colectiv.server.Models;

/*
    Not used as of now
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

/**
 * Yield model
 * Represents the yield of a specific seed
 * Contains the following fields:
 * - id: UUID - the id of the yield (primary key)
 * - name: String - the name of the yield
 * - description: String - the description of the yield
 * - quantity: double - the quantity of the yield
 * - user: User - the user that owns the yield
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "yields", schema = "public")
public class Yield {

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

    @NotEmpty(message = "Try again! Food quantity cannot be empty")
    @Column(name = "quantity")
    private double quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
