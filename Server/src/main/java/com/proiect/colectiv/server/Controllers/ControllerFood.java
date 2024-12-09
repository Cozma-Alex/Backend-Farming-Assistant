package com.proiect.colectiv.server.Controllers;

import com.proiect.colectiv.server.Models.Food;
import com.proiect.colectiv.server.Persistence.RepositoryFood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerFood {

    @Autowired
    private RepositoryFood repositoryFood;

    @PostMapping("/foods")
    public ResponseEntity<Food> saveFood(@RequestBody Food food, @RequestHeader("Authorization") String token) {
        return repositoryFood.save(UUID.fromString(token), food)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}
