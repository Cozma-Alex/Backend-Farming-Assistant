package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositoryLocation extends JpaRepository<Location, UUID> {
}
