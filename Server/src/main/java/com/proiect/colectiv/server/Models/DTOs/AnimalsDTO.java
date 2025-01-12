package com.proiect.colectiv.server.Models.DTOs;


import com.proiect.colectiv.server.Models.Animal;
import com.proiect.colectiv.server.Models.FoodProgramme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for Animals
 * Contains an Animal and a list of FoodProgrammes
 * Used to transfer data between the server and the client
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimalsDTO {
    private Animal animal;
    private List<FoodProgramme> foodProgrammes;

}
