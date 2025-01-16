package com.proiect.colectiv.server.Controllers;

import com.proiect.colectiv.server.Models.Task;
import com.proiect.colectiv.server.Models.Enums.TaskPriority;
import com.proiect.colectiv.server.Persistence.RepositoryTask;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Task Management", description = "APIs for managing farm tasks")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerTask {

    @Autowired
    private RepositoryTask repositoryTask;


    @Operation(
            summary = "Get all tasks of a user",
            description = "Retrieves all tasks belonging to the authenticated user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of tasks retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = Task.class))
            )
    )
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasksOfUser(
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        var tasks = repositoryTask.getTasksOfUser(UUID.fromString(token));
        return ResponseEntity.ok(verifyTaskPriority(tasks));
    }


    @Operation(
            summary = "Get task by ID",
            description = "Retrieves a specific task by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task found and returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Task.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content
            )
    })
    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<Task> getTaskOfUser(
            @PathVariable @Parameter(description = "UUID of the task") UUID task_id,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        var task = repositoryTask.getTaskOfUser(UUID.fromString(token), task_id);
        return task.map(value -> ResponseEntity.ok(verifyTaskPriority(List.of(value)).get(0))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get tasks by priority",
            description = "Retrieves all tasks with specified priority"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of tasks retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = Task.class))
            )
    )
    @GetMapping("/tasks/filters/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(
            @PathVariable @Parameter(description = "Task priority (HIGH, MEDIUM, LOW)") TaskPriority priority,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        var tasks = repositoryTask.getTasksByPriority(UUID.fromString(token), priority);
        return ResponseEntity.ok(verifyTaskPriority(tasks));
    }

    @Operation(
            summary = "Create new task",
            description = "Creates a new task for the authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task successfully created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Task.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authorized",
                    content = @Content
            )
    })
    @PostMapping("/tasks")
    public ResponseEntity<Task> saveTask(
            @RequestBody Task task,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        System.out.println(task);
        return repositoryTask.save(UUID.fromString(token), task)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @Operation(
            summary = "Update task",
            description = "Updates an existing task"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task successfully updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Task.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Task ID in path does not match body",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authorized",
                    content = @Content
            )
    })
    @PutMapping("/tasks/{task_id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable @Parameter(description = "UUID of the task") UUID task_id,
            @RequestBody Task task,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        if (!task_id.equals(task.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return repositoryTask.save(UUID.fromString(token), task)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @Operation(
            summary = "Delete task",
            description = "Deletes a specific task"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content
            )
    })
    @DeleteMapping("/tasks/{task_id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable @Parameter(description = "UUID of the task") UUID task_id,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        var deletedRows = repositoryTask.deleteById(UUID.fromString(token), task_id);

        return deletedRows == 1 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Get tasks by completion status",
            description = "Retrieves all tasks with specified completion status"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of tasks retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = Task.class))
            )
    )
    @GetMapping("/tasks/filters/done/{done}")
    public ResponseEntity<List<Task>> findTasksByCompletionStatus(
            @PathVariable @Parameter(description = "Completion status (true/false)") boolean done,
            @RequestHeader("Authorization") @Parameter(description = "User UUID token") String token) {
        var tasks = repositoryTask.findTasksByCompletionStatus(UUID.fromString(token), done);
        return ResponseEntity.ok(verifyTaskPriority(tasks));
    }

    /**
     * Verifies the priority of the tasks and updates them if necessary
     *
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
            if (task.getChangeToHighPriority() != null && task.getChangeToHighPriority().isBefore(LocalDateTime.now())) {
                task.setPriority(TaskPriority.HIGH);
                task.setChangeToHighPriority(null);
                repositoryTask.save(task);
                tasks.set(i, task);
            }
        }
        return tasks;
    }
}
