package com.proiect.colectiv.server.Controllers;

import com.proiect.colectiv.server.Models.Animal;
import com.proiect.colectiv.server.Models.DTOs.AnimalsDTO;
import com.proiect.colectiv.server.Models.FoodProgramme;
import com.proiect.colectiv.server.Persistence.RepositoryAnimal;
import com.proiect.colectiv.server.Persistence.RepositoryFoodProgramme;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Tag(name = "Animal Management", description = "APIs for managing farm animals and their food programmes")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerAnimal {

    @Autowired
    private RepositoryAnimal repositoryAnimal;


    @Operation(
            summary = "Save new animal",
            description = "Creates a new animal for a specific location"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Animal successfully saved",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Animal.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not the owner of the location",
                    content = @Content
            )
    })
    @PostMapping("/animals")
    public ResponseEntity<Animal> saveAnimal(
            @RequestBody Animal animal,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        return repositoryAnimal.save(UUID.fromString(token), animal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }


    @Operation(
            summary = "Get all animals of a user",
            description = "Retrieves all animals belonging to the authenticated user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of animals retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = Animal.class))
            )
    )
    @GetMapping("/animals")
    public ResponseEntity<List<Animal>> getAnimalsOfUser(
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        return ResponseEntity.ok(repositoryAnimal.getAnimalsOfUser(UUID.fromString(token)).orElse(null));
    }


    @Operation(
            summary = "Get animal by ID",
            description = "Retrieves a specific animal by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Animal found and returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Animal.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Animal not found",
                    content = @Content
            )
    })
    @GetMapping("/animals/{animal_id}")
    public ResponseEntity<Animal> getAnimalById(
            @PathVariable @Parameter(description = "UUID of the animal") UUID animal_id,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        return repositoryAnimal.findById(UUID.fromString(token), animal_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }


    @Operation(
            summary = "Update animal",
            description = "Updates an existing animal's information"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Animal successfully updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Animal.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Animal ID in path does not match body",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not the owner of the location",
                    content = @Content
            )
    })
    @PutMapping("/animals/{animal_id}")
    public ResponseEntity<Animal> updateAnimal(
            @PathVariable @Parameter(description = "UUID of the animal") UUID animal_id,
            @RequestBody Animal animal,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        if (!animal_id.equals(animal.getId())) {
            return ResponseEntity.status(400).build();
        }

        return repositoryAnimal.update(UUID.fromString(token), animal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }


    @Operation(
            summary = "Delete animal",
            description = "Deletes an animal by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Animal successfully deleted",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Animal.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not the owner of the location",
                    content = @Content
            )
    })
    @DeleteMapping("/animals/{animal_id}")
    public ResponseEntity<Animal> deleteAnimal(
            @PathVariable @Parameter(description = "UUID of the animal") UUID animal_id,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        return repositoryAnimal.deleteById(UUID.fromString(token), animal_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }


    @Operation(
            summary = "Get animals by location",
            description = "Retrieves all animals in a specific location"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of animals with their food programmes retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = AnimalsDTO.class))
            )
    )
    @GetMapping("/animals/location/{location_id}")
    public ResponseEntity<List<AnimalsDTO>> getAnimalsByLocation(
            @PathVariable @Parameter(description = "UUID of the location") UUID location_id,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        List<AnimalsDTO> animalsDTOS = new ArrayList<>();
        var animals = repositoryAnimal.getAnimalsByLocation(UUID.fromString(token), location_id);
        for (var animal : animals) {
            var foodProgrammes = repositoryFoodProgramme.getFoodProgrammesOfAnimal(UUID.fromString(token), animal.getId());
            animalsDTOS.add(new AnimalsDTO(animal, foodProgrammes));
        }

        return ResponseEntity.ok(animalsDTOS);
    }

    @Autowired
    private RepositoryFoodProgramme repositoryFoodProgramme;


    @Operation(
            summary = "Save food programmes",
            description = "Saves a list of food programmes for an animal"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Food programmes successfully saved",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = FoodProgramme.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Empty food programme list",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not the owner of the location",
                    content = @Content
            )
    })
    @PostMapping("/animals/food-programmes")
    public ResponseEntity<List<FoodProgramme>> saveFoodProgrammes(
            @RequestBody List<FoodProgramme> foodProgrammes,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        if (foodProgrammes.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        repositoryFoodProgramme.deleteAllByAnimal(UUID.fromString(token), foodProgrammes.get(0).getAnimal());

        return repositoryFoodProgramme.saveAll(UUID.fromString(token), foodProgrammes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }


    @Operation(
            summary = "Get food programmes of an animal",
            description = "Retrieves all food programmes for a specific animal"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of food programmes retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = FoodProgramme.class))
            )
    )
    @GetMapping("/animals/{animal_id}/food-programmes")
    public ResponseEntity<List<FoodProgramme>> getFoodProgrammesOfAnimal(
            @PathVariable @Parameter(description = "UUID of the animal") UUID animal_id,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        return ResponseEntity.ok(repositoryFoodProgramme.getFoodProgrammesOfAnimal(UUID.fromString(token), animal_id));
    }
}
