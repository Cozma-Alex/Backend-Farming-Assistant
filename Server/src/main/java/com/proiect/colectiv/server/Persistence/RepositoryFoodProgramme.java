package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.FoodProgramme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositoryFoodProgramme extends JpaRepository<FoodProgramme, UUID> {
}
