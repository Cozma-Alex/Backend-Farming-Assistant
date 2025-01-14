package com.proiect.colectiv.server.Models;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Food entity representing animal feed or other food items")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "foods", schema = "public")
public class Food {

    @Schema(description = "Unique identifier of the food item", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Schema(description = "Name of the food item", maxLength = 150)
    @Length(max = 150, message = "Food name is too long")
    @NotEmpty(message = "Try again! Food name cannot be empty")
    @Column(name = "name")
    private String name;

    @Schema(description = "Description of the food item", maxLength = 255, nullable = true)
    @Length(max = 255, message = "Description is too long")
    @Column(name = "description")
    private String description;

    @Schema(description = "Quantity of the food item")
    @NotEmpty(message = "Try again! Food quantity cannot be empty")
    @Column(name = "quantity")
    private double quantity;

    @Schema(description = "User who owns the food item")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
