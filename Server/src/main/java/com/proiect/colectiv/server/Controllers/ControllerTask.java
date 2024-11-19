package com.proiect.colectiv.server.Controllers;

import com.proiect.colectiv.server.Models.Task;
import com.proiect.colectiv.server.Models.TaskPriority;
import com.proiect.colectiv.server.Persistence.RepositoryTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerTask {

    @Autowired
    private RepositoryTask repositoryTask;

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasksOfUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(repositoryTask.getTasksOfUser(UUID.fromString(token)));
    }

    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<Task> getTaskOfUser(@PathVariable UUID task_id, @RequestHeader("Authorization") String token){
        return repositoryTask.getTaskOfUser(UUID.fromString(token), task_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tasks/filters/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable TaskPriority priority, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(repositoryTask.getTasksByPriority(UUID.fromString(token), priority));
    }

}
