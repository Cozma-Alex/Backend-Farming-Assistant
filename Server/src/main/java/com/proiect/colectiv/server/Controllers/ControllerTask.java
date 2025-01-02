package com.proiect.colectiv.server.Controllers;

import com.proiect.colectiv.server.Models.Task;
import com.proiect.colectiv.server.Models.TaskPriority;
import com.proiect.colectiv.server.Persistence.RepositoryTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerTask {

    @Autowired
    private RepositoryTask repositoryTask;

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasksOfUser(@RequestHeader("Authorization") String token) {
        var tasks = repositoryTask.getTasksOfUser(UUID.fromString(token));
        return ResponseEntity.ok(verifyTaskPriority(tasks));
    }

    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<Task> getTaskOfUser(@PathVariable UUID task_id, @RequestHeader("Authorization") String token) {
        var task = repositoryTask.getTaskOfUser(UUID.fromString(token), task_id);
        return task.map(value -> ResponseEntity.ok(verifyTaskPriority(List.of(value)).get(0))).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/tasks/filters/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable TaskPriority priority, @RequestHeader("Authorization") String token) {
        var tasks = repositoryTask.getTasksByPriority(UUID.fromString(token), priority);
        return ResponseEntity.ok(verifyTaskPriority(tasks));
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> saveTask(@RequestBody Task task, @RequestHeader("Authorization") String token) {
        System.out.println(task);
        return repositoryTask.save(UUID.fromString(token), task)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }


    @PutMapping("/tasks/{task_id}")
    public ResponseEntity<Task> updateTask(@PathVariable UUID task_id, @RequestBody Task task, @RequestHeader("Authorization") String token) {
        if (!task_id.equals(task.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return repositoryTask.save(UUID.fromString(token), task)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @DeleteMapping("/tasks/{task_id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID task_id, @RequestHeader("Authorization") String token) {
        var deletedRows = repositoryTask.deleteById(UUID.fromString(token), task_id);

        return deletedRows == 1 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/tasks/filters/done/{done}")
    public ResponseEntity<List<Task>> findTasksByCompletionStatus(@PathVariable boolean done, @RequestHeader("Authorization") String token) {
        var tasks = repositoryTask.findTasksByCompletionStatus(UUID.fromString(token), done);
        return ResponseEntity.ok(verifyTaskPriority(tasks));
    }

    private List<Task> verifyTaskPriority(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getChangeToMediumPriority() != null && task.getChangeToMediumPriority().isBefore(LocalDateTime.now())) {
                task.setPriority(TaskPriority.MEDIUM);
                task.setChangeToMediumPriority(null);
                repositoryTask.save(task);
                tasks.set(i, task);
            }
            if(task.getChangeToHighPriority() != null && task.getChangeToHighPriority().isBefore(LocalDateTime.now())) {
                task.setPriority(TaskPriority.HIGH);
                task.setChangeToHighPriority(null);
                repositoryTask.save(task);
                tasks.set(i, task);
            }
        }
        return tasks;
    }
}
