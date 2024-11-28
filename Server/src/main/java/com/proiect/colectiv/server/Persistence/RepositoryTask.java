package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Task;
import com.proiect.colectiv.server.Models.TaskPriority;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Transactional
    @Modifying
    default Optional<Task> save(UUID user_id, Task entity){
        if (!entity.getUser().getId().equals(user_id)) {
            return Optional.empty();
        }
        return Optional.of(save(entity));
    };

    @Transactional
    @Modifying
    @Query("delete from Task t where t.id = ?2 and t.user.id = ?1")
    int deleteById(UUID user_id, UUID task_id);

    @Query("select t from Task t where t.user.id = ?1 and t.done = ?2")
    List<Task> findTasksByCompletionStatus(UUID user_id, boolean done);

}
