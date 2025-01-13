package com.proiect.colectiv.server.Controllers;

import com.proiect.colectiv.server.Models.Animal;
import com.proiect.colectiv.server.Models.DTOs.AnimalsDTO;
import com.proiect.colectiv.server.Models.FoodProgramme;
import com.proiect.colectiv.server.Persistence.RepositoryAnimal;
import com.proiect.colectiv.server.Persistence.RepositoryFoodProgramme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerAnimal {

    @Autowired
    private RepositoryAnimal repositoryAnimal;

    /**
     * Save an animal
     * @param animal the animal to be saved
     * @param token the token of the user - "Authorization" header with the used UUID as key
     * @return the saved animal or 401 if the user is not the owner of the location
     */
    @PostMapping("/animals")
    public ResponseEntity<Animal> saveAnimal(@RequestBody Animal animal, @RequestHeader("Authorization") String token){
        return repositoryAnimal.save(UUID.fromString(token), animal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    /**
     * Get all animals of a user
     * @param token the token of the user
     * @return the animals of the user or null if the user has no animals
     */
    @GetMapping("/animals")
    public ResponseEntity<List<Animal>> getAnimalsOfUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(repositoryAnimal.getAnimalsOfUser(UUID.fromString(token)).orElse(null));
    }

    /**
     * Get an animal by id
     * @param animal_id the id of the animal
     * @param token the token of the user
     * @return the animal or 404 if the animal does not exist
     */
    @GetMapping("/animals/{animal_id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable UUID animal_id, @RequestHeader("Authorization") String token){
        return repositoryAnimal.findById(UUID.fromString(token), animal_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    /**
     * Update an animal
     * @param animal_id the id of the animal
     * @param animal the animal to be updated
     * @param token the token of the user
     * @return the updated animal or 401 if the user is not the owner of the location
     */
    @PutMapping("/animals/{animal_id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable UUID animal_id, @RequestBody Animal animal, @RequestHeader("Authorization") String token){
        if (!animal_id.equals(animal.getId())){
            return ResponseEntity.status(400).build();
        }

        return repositoryAnimal.update(UUID.fromString(token), animal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    /**
     * Delete an animal
     * @param animal_id the id of the animal
     * @param token the token of the user
     * @return the deleted animal or 401 if the user is not the owner of the location
     */
    @DeleteMapping("/animals/{animal_id}")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable UUID animal_id, @RequestHeader("Authorization") String token){
        return repositoryAnimal.deleteById(UUID.fromString(token), animal_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    /**
     * Get all animals of a location
     * @param location_id the id of the location
     * @param token the token of the user
     * @return the animals of the location
     */
    @GetMapping("/animals/location/{location_id}")
    public ResponseEntity<List<AnimalsDTO>> getAnimalsByLocation(@PathVariable UUID location_id, @RequestHeader("Authorization") String token){
        List<AnimalsDTO> animalsDTOS = new ArrayList<>();
        var animals =  repositoryAnimal.getAnimalsByLocation(UUID.fromString(token), location_id);
        for(var animal : animals){
            var foodProgrammes = repositoryFoodProgramme.getFoodProgrammesOfAnimal(UUID.fromString(token), animal.getId());
            animalsDTOS.add(new AnimalsDTO(animal, foodProgrammes));
        }

        return ResponseEntity.ok(animalsDTOS);
    }


    @Autowired
    private RepositoryFoodProgramme repositoryFoodProgramme;

    /**
     * Save a list of food programmes
     * @param foodProgrammes the list of food programmes to be saved
     * @param token the token of the user
     * @return the saved food programmes or 401 if the user is not the owner of the location
     */
    @PostMapping("/animals/food-programmes")
    public ResponseEntity<List<FoodProgramme>> saveFoodProgrammes(@RequestBody List<FoodProgramme> foodProgrammes, @RequestHeader("Authorization") String token){
        if(foodProgrammes.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        repositoryFoodProgramme.deleteAllByAnimal(UUID.fromString(token), foodProgrammes.get(0).getAnimal());

        return repositoryFoodProgramme.saveAll(UUID.fromString(token), foodProgrammes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    /**
     * Get all food programmes of a user
     * @param animal_id the id of the animal
     * @param token the token of the user
     * @return the food programmes of the animal
     */
    @GetMapping("/animals/{animal_id}/food-programmes")
    public ResponseEntity<List<FoodProgramme>> getFoodProgrammesOfAnimal(@PathVariable UUID animal_id, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(repositoryFoodProgramme.getFoodProgrammesOfAnimal(UUID.fromString(token), animal_id));
    }
}
