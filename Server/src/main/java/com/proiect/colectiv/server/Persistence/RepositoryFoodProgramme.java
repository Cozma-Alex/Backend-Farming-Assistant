package com.proiect.colectiv.server.Persistence;

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

    @Transactional
    @Modifying
    default Optional<List<FoodProgramme>> saveAll(UUID user_id, List<FoodProgramme> entities){
        if (!entities.get(0).getAnimal().getLocation().getUser().getId().equals(user_id)) {
            return Optional.empty();
        }
        return Optional.of(saveAll(entities));
    };


    @Query("SELECT f FROM FoodProgramme f WHERE f.animal.id = :animal_id AND f.animal.location.user.id = :user_id")
    List<FoodProgramme> getFoodProgrammesOfAnimal(UUID user_id, UUID animal_id);
}
