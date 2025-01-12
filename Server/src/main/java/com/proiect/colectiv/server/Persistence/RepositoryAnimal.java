package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Animal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RepositoryAnimal extends JpaRepository<Animal, UUID> {

    /**
     * Save an animal
     * @param user_id the id of the user
     * @param entity the animal to be saved
     * @return the saved animal
     */
    @Transactional
    @Modifying
    default Optional<Animal> save(UUID user_id, Animal entity){
        if (!entity.getLocation().getUser().getId().equals(user_id)) {
            return Optional.empty();
        }
        return Optional.of(save(entity));
    };

    /**
     * Update an animal
     * @param user_id the id of the user
     * @param entity the animal to be updated
     * @return the updated animal
     */
    @Transactional
    @Modifying
    default Optional<Animal> update(UUID user_id, Animal entity){
        if (!entity.getLocation().getUser().getId().equals(user_id)) {
            return Optional.empty();
        }
        return Optional.of(save(entity));
    };

    /**
     * Delete an animal
     * @param user_id the id of the user
     * @param id the id of the animal to be deleted
     * @return the deleted animal
     */
    @Transactional
    @Modifying
    @Query("delete from Animal a where a.id = ?2 and a.location.user.id = ?1")
    Optional<Animal> deleteById(UUID user_id, UUID id);

    /**
     * Get all animals of a user
     * @param user_id the id of the user
     * @return the list of animals
     */
    @Query("select a from Animal a where a.location.user.id = ?1")
    Optional<List<Animal>> getAnimalsOfUser(UUID user_id);

    /**
     * Find an animal by id
     * @param user_id the id of the user
     * @param id the id of the animal
     * @return the animal if found
     */
    @Query("select a from Animal a where a.location.user.id = ?1 and a.id = ?2")
    Optional<Animal> findById(UUID user_id, UUID id);

    /**
     * Get all animals of a location
     * @param user_id the id of the user
     * @param location_id the id of the location
     * @return the list of animals
     */
    @Query("select a from Animal a where a.location.user.id = ?1 and a.location.id = ?2")
    List<Animal> getAnimalsByLocation(UUID user_id, UUID location_id);

}
