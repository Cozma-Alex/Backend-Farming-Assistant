package com.proiect.colectiv.server.Controllers;

import com.proiect.colectiv.server.Models.Animal;
import com.proiect.colectiv.server.Models.FoodProgramme;
import com.proiect.colectiv.server.Persistence.RepositoryAnimal;
import com.proiect.colectiv.server.Persistence.RepositoryFoodProgramme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerAnimal {

    @Autowired
    private RepositoryAnimal repositoryAnimal;

    @PostMapping("/animals")
    public ResponseEntity<Animal> saveAnimal(@RequestBody Animal animal, @RequestHeader("Authorization") String token){
        return repositoryAnimal.save(UUID.fromString(token), animal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping("/animals")
    public ResponseEntity<List<Animal>> getAnimalsOfUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(repositoryAnimal.getAnimalsOfUser(UUID.fromString(token)).orElse(null));
    }

    @GetMapping("/animals/{animal_id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable UUID animal_id, @RequestHeader("Authorization") String token){
        return repositoryAnimal.findById(UUID.fromString(token), animal_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    @PutMapping("/animals/{animal_id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable UUID animal_id, @RequestBody Animal animal, @RequestHeader("Authorization") String token){
        if (!animal_id.equals(animal.getId())){
            return ResponseEntity.status(400).build();
        }

        return repositoryAnimal.update(UUID.fromString(token), animal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @DeleteMapping("/animals/{animal_id}")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable UUID animal_id, @RequestHeader("Authorization") String token){
        return repositoryAnimal.deleteById(UUID.fromString(token), animal_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping("/animals/location/{location_id}")
    public ResponseEntity<List<Animal>> getAnimalsByLocation(@PathVariable UUID location_id, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(repositoryAnimal.getAnimalsByLocation(UUID.fromString(token), location_id).orElse(null));
    }



    @Autowired
    private RepositoryFoodProgramme repositoryFoodProgramme;


    @PostMapping("/animals/food-programmes")
    public ResponseEntity<List<FoodProgramme>> saveFoodProgrammes(@RequestBody List<FoodProgramme> foodProgrammes, @RequestHeader("Authorization") String token){
        return repositoryFoodProgramme.saveAll(UUID.fromString(token), foodProgrammes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping("/animals/{animal_id}/food-programmes")
    public ResponseEntity<List<FoodProgramme>> getFoodProgrammesOfAnimal(@PathVariable UUID animal_id, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(repositoryFoodProgramme.getFoodProgrammesOfAnimal(UUID.fromString(token), animal_id));
    }
}
