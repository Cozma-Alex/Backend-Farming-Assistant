package com.proiect.colectiv.server.Models;

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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "food_programmes", schema = "public")
public class FoodProgramme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "start_hour")
    private LocalTime startHour;

    @Column(name = "end_hour")
    private LocalTime endHour;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
}
