package com.proiect.colectiv.server.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

/**
 * FoodProgramme entity
 * Represents the food programme of an animal having the following fields:
 * - id: UUID - the id of the food programme (primary key)
 * - startHour: LocalTime - the start hour of the food programme (not null)
 * - endHour: LocalTime - the end hour of the food programme (optional)
 * - animal: Animal - the animal that has the food programme (foreign key, not null)
 * - food: Food - the food that is in the food programme (foreign key, not null)
 */
@Schema(description = "Food Programme entity representing feeding schedules for animals")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "food_programmes", schema = "public")
public class FoodProgramme {

    @Schema(description = "Unique identifier of the food programme", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Schema(description = "Start time of feeding", example = "08:00")
    @Column(name = "start_hour")
    private LocalTime startHour;

    @Schema(description = "End time of feeding", example = "09:00", nullable = true)
    @Column(name = "end_hour")
    private LocalTime endHour;

    @Schema(description = "Animal this food programme is for")
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Schema(description = "Food item to be fed")
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
}
