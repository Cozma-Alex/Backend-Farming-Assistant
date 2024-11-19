package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Task;
import com.proiect.colectiv.server.Models.TaskPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RepositoryTask extends JpaRepository<Task, UUID> {

    @Query("select t from Task t where t.user.id = ?1")
    List<Task> getTasksOfUser(UUID user_id);

    @Query("select t from Task t where t.user.id = ?1 and t.priority = ?2")
    List<Task> getTasksByPriority(UUID user_id, TaskPriority priority);

    @Query("select t from Task t where t.user.id = ?1 and t.id = ?2")
    Optional<Task> getTaskOfUser(UUID user_id, UUID task_id);
}
