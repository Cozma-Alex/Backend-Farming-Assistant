package com.proiect.colectiv.server.Controllers;

import com.proiect.colectiv.server.Models.User;
import com.proiect.colectiv.server.Persistence.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerUser {

    @Autowired
    private RepositoryUser repositoryUser;

    @PostMapping("/users/auth")
    public ResponseEntity<User> login(@RequestBody User user) {
        return repositoryUser.findByEmailAndPassword(user.getEmail(), user.getPassword_hash())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(repositoryUser.save(user));
    }

    @PutMapping("/users")
    public ResponseEntity<User> update(@RequestBody User user){
        return ResponseEntity.ok(repositoryUser.update(user));
    }
}
