package com.proiect.colectiv.server.Models;

import com.proiect.colectiv.server.Models.Enums.Section;
import com.proiect.colectiv.server.Models.Enums.TaskPriority;
import com.proiect.colectiv.server.Models.Enums.TaskRecurrence;
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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tasks", schema = "public")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Length(max = 150, message = "Task name is too long")
    @NotEmpty(message = "Try again! Task name cannot be empty")
    @Column(name = "name")
    private String name;

    @Length(max = 255, message = "Description is too long")
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence")
    private TaskRecurrence recurrence;

    @Column(name = "change_to_medium_priority")
    private LocalDateTime changeToMediumPriority;

    @Column(name = "change_to_high_priority")
    private LocalDateTime changeToHighPriority;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "section")
    private Section section;

    @Column(name = "done")
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
