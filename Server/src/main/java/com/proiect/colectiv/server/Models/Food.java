package com.proiect.colectiv.server.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

/**
 * Food model
 * Contains the following fields:
 * - id: UUID - the id of the food item (primary key)
 * - name: String - the name of the food item
 * - description: String - the description of the food item (optional)
 * - quantity: double - the quantity of the food item
 * - user: User - the user that owns the food item
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "foods", schema = "public")
public class Food {

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
