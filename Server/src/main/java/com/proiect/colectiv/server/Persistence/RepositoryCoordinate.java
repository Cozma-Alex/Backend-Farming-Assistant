package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepositoryCoordinate extends JpaRepository<Coordinate, UUID> {

    /**
     * Get all coordinates of a location
     * @param location_id the id of the location
     * @return the list of coordinates
     */
    @Query("select c from Coordinate c where c.location.id = ?1")
    List<Coordinate> getCoordinatesOfLocation(UUID location_id);
}
