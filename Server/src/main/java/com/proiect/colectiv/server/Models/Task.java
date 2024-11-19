package com.proiect.colectiv.server.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @NotEmpty(message = "Try again! Task type cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TaskRecurrence type;

    @Column(name = "change_to_medium")
    private LocalDateTime change_to_medium;

    @Column(name = "change_to_high")
    private LocalDateTime change_to_high;

    @NotEmpty(message = "Try again! Task deadline cannot be empty")
    @Column(name = "deadline")
    private LocalDateTime deadline;

    @NotEmpty(message = "Try again! Task priority cannot be emtpy")
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;

    @NotEmpty(message = "Try again! Task section cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(name = "section")
    private Section section;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
