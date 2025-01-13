package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Animal;
import com.proiect.colectiv.server.Models.FoodProgramme;
import com.proiect.colectiv.server.Models.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RepositoryFoodProgramme extends JpaRepository<FoodProgramme, UUID> {

    /**
     * Save a food programme entity
     * @param user_id the id of the user
     * @param entities the entities to be saved
     * @return the saved entities or empty if the user is not the owner
     */
    @Transactional
    @Modifying
    default Optional<List<FoodProgramme>> saveAll(UUID user_id, List<FoodProgramme> entities){
        if (!entities.get(0).getAnimal().getLocation().getUser().getId().equals(user_id)) {
            return Optional.empty();
        }
        return Optional.of(saveAll(entities));
    };

    /**
     * Get all the food programmes of an animal
     * @param user_id the id of the user
     * @param animal_id the id of the animal
     * @return a list of food programmes
     */
    @Query("SELECT f FROM FoodProgramme f WHERE f.animal.id = :animal_id AND f.animal.location.user.id = :user_id")
    List<FoodProgramme> getFoodProgrammesOfAnimal(UUID user_id, UUID animal_id);

    @Modifying
    @Transactional
    @Query("DELETE FROM FoodProgramme f WHERE f.animal.id = :#{#animal.id} AND f.animal.location.user.id = :user_id")
    void deleteAllByAnimal(UUID user_id, Animal animal);
}
