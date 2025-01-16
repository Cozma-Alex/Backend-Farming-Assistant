package com.proiect.colectiv.server.Models;

import com.proiect.colectiv.server.Models.Enums.Section;
import com.proiect.colectiv.server.Models.Enums.TaskPriority;
import com.proiect.colectiv.server.Models.Enums.TaskRecurrence;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Task model
 * Contains all the information about a task with the following fields:
 * - id: UUID - the unique identifier of the task (primary key)
 * - name: String - the name of the task (max 150 characters)
 * - description: String - the description of the task (max 255 characters)
 * - recurrence: TaskRecurrence - the recurrence of the task (daily, weekly, monthly, yearly)
 * - changeToMediumPriority: LocalDateTime - the date when the task should change to medium priority
 * - changeToHighPriority: LocalDateTime - the date when the task should change to high priority
 * - deadline: LocalDateTime - the deadline of the task
 * - priority: TaskPriority - the priority of the task (low, medium, high)
 * - section: Section - the section of the task
 * - done: boolean - the status of the task (done or not done)
 * - user: User - the user that the task belongs to
 */
@Schema(description = "Task entity representing a farm task")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tasks", schema = "public")
public class Task {

    @Schema(description = "Unique identifier of the task", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Schema(description = "Name of the task", maxLength = 150)
    @Length(max = 150, message = "Task name is too long")
    @NotEmpty(message = "Try again! Task name cannot be empty")
    @Column(name = "name")
    private String name;

    @Schema(description = "Description of the task", maxLength = 255, nullable = true)
    @Length(max = 255, message = "Description is too long")
    @Column(name = "description")
    private String description;

    @Schema(description = "Task recurrence pattern (NONE, DAILY, WEEKLY, MONTHLY)")
    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence")
    private TaskRecurrence recurrence;

    @Schema(description = "Date when task priority should change to medium", nullable = true)
    @Column(name = "change_to_medium_priority")
    private LocalDateTime changeToMediumPriority;

    @Schema(description = "Date when task priority should change to high", nullable = true)
    @Column(name = "change_to_high_priority")
    private LocalDateTime changeToHighPriority;

    @Schema(description = "Task deadline", nullable = true)
    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Schema(description = "Task priority level (LOW, MEDIUM, HIGH)")
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;

    @Schema(description = "Task section (CROPS, ANIMALS, TOOLS, INVENTORY, OTHER)")
    @Enumerated(EnumType.STRING)
    @Column(name = "section")
    private Section section;

    @Schema(description = "Task completion status")
    @Column(name = "done")
    private boolean done;

    @Schema(description = "User who owns the task")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
