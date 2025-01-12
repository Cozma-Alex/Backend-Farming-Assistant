package com.proiect.colectiv.server.Controllers;

import com.proiect.colectiv.server.Models.Task;
import com.proiect.colectiv.server.Models.Enums.TaskPriority;
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

    /**
     * Get all tasks of a user
     * @param token the token of the user
     * @return the tasks of the user
     */
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasksOfUser(@RequestHeader("Authorization") String token) {
        var tasks = repositoryTask.getTasksOfUser(UUID.fromString(token));
        return ResponseEntity.ok(verifyTaskPriority(tasks));
    }

    /**
     * Get a task by id
     * @param task_id the id of the task
     * @param token the token of the user
     * @return the task or 404 if the task does not exist
     */
    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<Task> getTaskOfUser(@PathVariable UUID task_id, @RequestHeader("Authorization") String token) {
        var task = repositoryTask.getTaskOfUser(UUID.fromString(token), task_id);
        return task.map(value -> ResponseEntity.ok(verifyTaskPriority(List.of(value)).get(0))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Get tasks by priority
     * @param priority the priority of the tasks
     * @param token the token of the user
     * @return the tasks with the given priority
     */
    @GetMapping("/tasks/filters/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable TaskPriority priority, @RequestHeader("Authorization") String token) {
        var tasks = repositoryTask.getTasksByPriority(UUID.fromString(token), priority);
        return ResponseEntity.ok(verifyTaskPriority(tasks));
    }

    /**
     * Save a task
     * @param task the task to be saved
     * @param token the token of the user - "Authorization" header with the used UUID as key
     * @return the saved task or 401 if the user is not the owner of the location
     */
    @PostMapping("/tasks")
    public ResponseEntity<Task> saveTask(@RequestBody Task task, @RequestHeader("Authorization") String token) {
        System.out.println(task);
        return repositoryTask.save(UUID.fromString(token), task)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    /**
     * Update a task
     * @param task_id the id of the task
     * @param task the task to be updated
     * @param token the token of the user
     * @return the updated task or 401 if the user is not the owner of the location
     */
    @PutMapping("/tasks/{task_id}")
    public ResponseEntity<Task> updateTask(@PathVariable UUID task_id, @RequestBody Task task, @RequestHeader("Authorization") String token) {
        if (!task_id.equals(task.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return repositoryTask.save(UUID.fromString(token), task)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    /**
     * Delete a task
     * @param task_id the id of the task
     * @param token the token of the user
     * @return 200 if the task was deleted, 404 if the task does not exist
     */
    @DeleteMapping("/tasks/{task_id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID task_id, @RequestHeader("Authorization") String token) {
        var deletedRows = repositoryTask.deleteById(UUID.fromString(token), task_id);

        return deletedRows == 1 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    /**
     * Get tasks by completion status
     * @param done the completion status of the tasks
     * @param token the token of the user
     * @return the tasks with the given completion status
     */
    @GetMapping("/tasks/filters/done/{done}")
    public ResponseEntity<List<Task>> findTasksByCompletionStatus(@PathVariable boolean done, @RequestHeader("Authorization") String token) {
        var tasks = repositoryTask.findTasksByCompletionStatus(UUID.fromString(token), done);
        return ResponseEntity.ok(verifyTaskPriority(tasks));
    }

    /**
     * Verifies the priority of the tasks and updates them if necessary
     * @param tasks the tasks to be verified
     * @return the updated tasks
     */
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
