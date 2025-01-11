package com.proiect.colectiv.server.Controllers;


import com.proiect.colectiv.server.Models.Location;
import com.proiect.colectiv.server.Persistence.RepositoryLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerLocation {

    @Autowired
    private RepositoryLocation repositoryLocation;

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getLocationsOfUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(repositoryLocation.getLocationsByUser(UUID.fromString(token)));
    }
}
