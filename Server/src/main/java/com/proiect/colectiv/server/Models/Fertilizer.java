package com.proiect.colectiv.server.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

/**
 * Fertilizer model
 * This class is used to create a Fertilizer object having the following attributes:
 * - id: UUID - the id of the fertilizer (primary key)
 * - name: String - the name of the fertilizer (max 150 characters)
 * - description: String - the description of the fertilizer (optional - max 255 characters)
 * - quantity: double - the quantity of the fertilizer
 * - user: User - the user that owns the fertilizer (foreign key)
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "fertilizers", schema = "public")
public class Fertilizer {
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
