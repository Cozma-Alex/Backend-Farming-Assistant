package com.proiect.colectiv.server.Controllers;

import com.proiect.colectiv.server.Models.User;
import com.proiect.colectiv.server.Persistence.RepositoryUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "User Management", description = "APIs for managing users including authentication and profile updates")
public class ControllerUser {

    @Autowired
    private RepositoryUser repositoryUser;


    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user using email and password credentials"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully authenticated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found or invalid credentials",
                    content = @Content
            )
    })
    @PostMapping("/users/auth")
    public ResponseEntity<User> login(@RequestBody User user) {
        return repositoryUser.findByEmailAndPassword(user.getEmail(), user.getPasswordHash())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(
            summary = "Register new user",
            description = "Creates a new user account in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully registered",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            )
    })
    @PostMapping("/users")
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(repositoryUser.save(user));
    }


    @Operation(
            summary = "Update user profile",
            description = "Updates an existing user's information"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            )
    })
    @PutMapping("/users")
    public ResponseEntity<User> update(@RequestBody User user){
        return ResponseEntity.ok(repositoryUser.update(user));
    }
}
