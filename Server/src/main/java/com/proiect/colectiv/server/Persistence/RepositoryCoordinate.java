package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositoryCoordinate extends JpaRepository<Coordinate, UUID> {
}
