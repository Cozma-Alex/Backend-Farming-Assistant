package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositoryAnimal extends JpaRepository<Animal, UUID> {
}
