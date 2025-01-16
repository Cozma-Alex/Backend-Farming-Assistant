package com.proiect.colectiv.server.Controllers;

import com.proiect.colectiv.server.Models.Food;
import com.proiect.colectiv.server.Persistence.RepositoryFood;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Food Management", description = "APIs for managing food items and feeding programmes")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerFood {

    @Autowired
    private RepositoryFood repositoryFood;


    @Operation(
            summary = "Create new food item",
            description = "Creates a new food item for the authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Food item successfully created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Food.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authorized",
                    content = @Content
            )
    })
    @PostMapping("/foods")
    public ResponseEntity<Food> saveFood(
            @RequestBody Food food,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {

        return repositoryFood.save(UUID.fromString(token), food)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @Operation(
            summary = "Get all food items of a user",
            description = "Retrieves all food items belonging to the authenticated user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of food items retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = Food.class))
            )
    )
    @GetMapping("/foods")
    public ResponseEntity<List<Food>> getFoodsOfUser(
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token){

        return ResponseEntity.ok(repositoryFood.getFoodsOfUser(UUID.fromString(token)));
    }

}
