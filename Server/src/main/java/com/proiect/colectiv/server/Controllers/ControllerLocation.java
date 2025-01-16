package com.proiect.colectiv.server.Controllers;


import com.proiect.colectiv.server.Models.Coordinate;
import com.proiect.colectiv.server.Models.DTOs.LocationDTO;
import com.proiect.colectiv.server.Models.Location;
import com.proiect.colectiv.server.Models.Enums.LocationType;
import com.proiect.colectiv.server.Persistence.RepositoryCoordinate;

import com.proiect.colectiv.server.Persistence.RepositoryLocation;
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

@Tag(name = "Location Management", description = "APIs for managing farm locations and their coordinates")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerLocation {

    @Autowired
    private RepositoryLocation repositoryLocation;

    @Autowired
    private RepositoryCoordinate repositoryCoordinate;


    @Operation(
            summary = "Create new location",
            description = "Creates a new location with its coordinates"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Location successfully created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LocationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authorized",
                    content = @Content
            )
    })
    @PostMapping("/locations")
    public ResponseEntity<LocationDTO> saveLocation(
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token,
            @RequestBody LocationDTO locationDTO) {
        return getLocationDTOResponseEntity(token, locationDTO);
    }


    @Operation(
            summary = "Get all locations of a user",
            description = "Retrieves all locations belonging to the authenticated user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of locations retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = LocationDTO.class))
            )
    )
    @GetMapping("/locations")
    public ResponseEntity<List<LocationDTO>> getLocationsOfUser(
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {

        var locationsDTO = new ArrayList<LocationDTO>();
        var locations = repositoryLocation.getLocationsOfUser(UUID.fromString(token));
        for (Location location : locations) {
            var coordinates = repositoryCoordinate.getCoordinatesOfLocation(location.getId());
            locationsDTO.add(new LocationDTO(location, coordinates));
        }

        return ResponseEntity.ok(locationsDTO);
    }


    @Operation(
            summary = "Get location by ID",
            description = "Retrieves a specific location by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Location found and returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Location.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Location not found",
                    content = @Content
            )
    })
    @GetMapping("/locations/{location_id}")
    public ResponseEntity<Location> getLocationOfUser(
            @PathVariable @Parameter(description = "UUID of the location") UUID location_id,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {

        return repositoryLocation.getLocationOfUser(UUID.fromString(token), location_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(
            summary = "Update location",
            description = "Updates an existing location and its coordinates"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Location successfully updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LocationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Location ID in path does not match body",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authorized",
                    content = @Content
            )
    })
    @PostMapping("/locations/{location_id}")
    public ResponseEntity<LocationDTO> updateLocation(
            @PathVariable @Parameter(description = "UUID of the location") UUID location_id,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token,
            @RequestBody LocationDTO locationDTO) {

        if (!location_id.equals(locationDTO.getLocation().getId())) {
            return ResponseEntity.badRequest().build();
        }
        return getLocationDTOResponseEntity(token, locationDTO);
    }


    @Operation(
            summary = "Get locations by type",
            description = "Retrieves all locations of a specific type for the authenticated user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of locations retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = LocationDTO.class))
            )
    )
    @GetMapping("/locations/filter/{type}")
    public ResponseEntity<List<LocationDTO>> getLocationsByType(
            @PathVariable @Parameter(description = "Location type (FIELD, BARN, STORAGE, TOOLS)") String type,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {

        var locationsDTO = new ArrayList<LocationDTO>();
        var locations = repositoryLocation.getLocationsOfUserByType(UUID.fromString(token), LocationType.valueOf(type));
        for (Location location : locations) {
            var coordinates = repositoryCoordinate.getCoordinatesOfLocation(location.getId());
            locationsDTO.add(new LocationDTO(location, coordinates));
        }

        return ResponseEntity.ok(locationsDTO);
    }


    /**
     * Saves/Updates a location and its coordinates
     *
     * @param token       the token of the user
     * @param locationDTO the location to be saved/updated
     * @return the saved/updated location or 401 if the user is not the owner of the location as a response entity
     */
    private ResponseEntity<LocationDTO> getLocationDTOResponseEntity(@RequestHeader("Authorization") String token, @RequestBody LocationDTO locationDTO) {
        var savedLocation = repositoryLocation.save(UUID.fromString(token), locationDTO.getLocation());
        if (savedLocation.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        locationDTO.setLocation(savedLocation.get());

        for (Coordinate coordinate : locationDTO.getCoordinates()) {
            coordinate.setLocation(locationDTO.getLocation());
            repositoryCoordinate.save(coordinate);
        }
        return ResponseEntity.ok(locationDTO);
    }

}
