package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Task;
import com.proiect.colectiv.server.Models.Enums.TaskPriority;
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

    /**
     * Get all tasks of a user
     * @param user_id the id of the user
     * @return a list of tasks
     */
    @Query("select t from Task t where t.user.id = ?1")
    List<Task> getTasksOfUser(UUID user_id);

    /**
     * Get all tasks of a user with a specific priority
     * @param user_id the id of the user
     * @param priority the priority of the tasks
     * @return a list of tasks
     */
    @Query("select t from Task t where t.user.id = ?1 and t.priority = ?2")
    List<Task> getTasksByPriority(UUID user_id, TaskPriority priority);

    /**
     * Get a task of a user
     * @param user_id the id of the user
     * @param task_id the id of the task
     * @return the task
     */
    @Query("select t from Task t where t.user.id = ?1 and t.id = ?2")
    Optional<Task> getTaskOfUser(UUID user_id, UUID task_id);

    /**
     * Save a task
     * @param user_id the id of the user
     * @param entity the task to be saved
     * @return the saved task or an empty optional if the user is not the owner of the task
     */
    @Transactional
    @Modifying
    default Optional<Task> save(UUID user_id, Task entity){
        if (!entity.getUser().getId().equals(user_id)) {
            return Optional.empty();
        }
        return Optional.of(save(entity));
    };

    /**
     * Delete a task
     * @param user_id the id of the user
     * @param task_id the id of the task
     * @return the number of tasks deleted
     */
    @Transactional
    @Modifying
    @Query("delete from Task t where t.id = ?2 and t.user.id = ?1")
    int deleteById(UUID user_id, UUID task_id);

    /**
     * Find tasks by completion status
     * @param user_id the id of the user
     * @param done the completion status
     * @return a list of tasks
     */
    @Query("select t from Task t where t.user.id = ?1 and t.done = ?2")
    List<Task> findTasksByCompletionStatus(UUID user_id, boolean done);

}
