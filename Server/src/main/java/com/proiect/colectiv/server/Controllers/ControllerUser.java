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

    /**
     * Method for login
     * @param user - user object
     * @return the user object if it exists in the database
     */
    @PostMapping("/users/auth")
    public ResponseEntity<User> login(@RequestBody User user) {
        System.out.println(user);
        return repositoryUser.findByEmailAndPassword(user.getEmail(), user.getPasswordHash())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Method for saving a user
     * @param user - user object
     * @return the saved user
     */
    @PostMapping("/users")
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(repositoryUser.save(user));
    }

    /**
     * Method for updating a user
     * @param user - user object
     * @return the updated user
     */
    @PutMapping("/users")
    public ResponseEntity<User> update(@RequestBody User user){
        return ResponseEntity.ok(repositoryUser.update(user));
    }
}
