package com.proiect.colectiv.server.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

/**
 * Seeds model
 * Represents a seed in the database with the following fields:
 * - id: UUID - unique identifier of the seed (primary key)
 * - name: String - name of the seed (e.g. Tomato)
 * - description: String - description of the seed (e.g. type, color)
 * - quantity: double - quantity of the seed (e.g. 0.5 kg)
 * - user: User - user that owns the seed (foreign key)
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "seeds", schema = "public")
public class Seeds {

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
