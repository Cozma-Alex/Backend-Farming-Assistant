package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Food;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RepositoryFood extends JpaRepository<Food, UUID> {

    @Transactional
    @Modifying
    default Optional<Food> save(UUID user_id, Food entity){
        if (!entity.getUser().getId().equals(user_id)) {
            return Optional.empty();
        }
        return Optional.of(save(entity));
    };

    @Query("select f from Food f where f.user.id = ?1")
    List<Food> getFoodsOfUser(UUID user_id);
}
