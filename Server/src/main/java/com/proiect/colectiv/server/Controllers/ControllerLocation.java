package com.proiect.colectiv.server.Controllers;


import com.proiect.colectiv.server.Models.Coordinate;
import com.proiect.colectiv.server.Models.DTOs.LocationDTO;
import com.proiect.colectiv.server.Models.Location;
import com.proiect.colectiv.server.Models.Enums.LocationType;
import com.proiect.colectiv.server.Persistence.RepositoryCoordinate;

import com.proiect.colectiv.server.Persistence.RepositoryLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerLocation {

    @Autowired
    private RepositoryLocation repositoryLocation;

    @Autowired
    private RepositoryCoordinate repositoryCoordinate;

    /**
     * Save a location
     * @param token the token of the user - "Authorization" header with the used UUID as key
     * @param locationDTO the location to be saved
     * @return the saved location or 401 if the user is not the owner of the location
     */
    @PostMapping("/locations")
    public ResponseEntity<LocationDTO> saveLocation(@RequestHeader("Authorization") String token, @RequestBody LocationDTO locationDTO) {
        return getLocationDTOResponseEntity(token, locationDTO);
    }

    /**
     * Get all locations of a user
     * @param token the token of the user
     * @return the locations of the user
     */
    @GetMapping("/locations")
    public ResponseEntity<List<LocationDTO>> getLocationsOfUser(@RequestHeader("Authorization") String token) {
        var locationsDTO = new ArrayList<LocationDTO>();
        var locations = repositoryLocation.getLocationsOfUser(UUID.fromString(token));
        for (Location location : locations) {
            var coordinates = repositoryCoordinate.getCoordinatesOfLocation(location.getId());
            locationsDTO.add(new LocationDTO(location, coordinates));
        }

        return ResponseEntity.ok(locationsDTO);
    }

    /**
     * Get a location by id
     * @param location_id the id of the location
     * @param token the token of the user
     * @return the location or 404 if the location does not exist
     */
    @GetMapping("/locations/{location_id}")
    public ResponseEntity<Location> getLocationOfUser(@PathVariable UUID location_id, @RequestHeader("Authorization") String token) {
        return repositoryLocation.getLocationOfUser(UUID.fromString(token), location_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update a location
     * @param location_id the id of the location
     * @param token the token of the user
     * @param locationDTO the location to be updated
     * @return the updated location or 401 if the user is not the owner of the location
     */
    @PostMapping("/locations/{location_id}")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable UUID location_id, @RequestHeader("Authorization") String token, @RequestBody LocationDTO locationDTO) {
        if (!location_id.equals(locationDTO.getLocation().getId())) {
            return ResponseEntity.badRequest().build();
        }
        return getLocationDTOResponseEntity(token, locationDTO);
    }

    /**
     * Saves/Updates a location and its coordinates
     * @param token the token of the user
     * @param locationDTO the location to be saved/updated
     * @return the saved/updated location or 401 if the user is not the owner of the location
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

    /**
     * Get all locations of a user by type
     * @param type the type of the location
     * @param token the token of the user
     * @return the locations of the user by type
     */
    @GetMapping("/locations/filter/{type}")
    public ResponseEntity<List<LocationDTO>> getLocationsByType(@PathVariable String type, @RequestHeader("Authorization") String token) {
        var locationsDTO = new ArrayList<LocationDTO>();
        var locations = repositoryLocation.getLocationsOfUserByType(UUID.fromString(token), LocationType.valueOf(type));
        for (Location location : locations) {
            var coordinates = repositoryCoordinate.getCoordinatesOfLocation(location.getId());
            locationsDTO.add(new LocationDTO(location, coordinates));
        }

        return ResponseEntity.ok(locationsDTO);
    }
}
