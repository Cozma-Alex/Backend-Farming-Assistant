package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepositoryLocation extends JpaRepository<Location, UUID> {

    @Query("select l from Location l where l.user.id = ?1")
    List<Location> getLocationsByUser(UUID user_id);
}
