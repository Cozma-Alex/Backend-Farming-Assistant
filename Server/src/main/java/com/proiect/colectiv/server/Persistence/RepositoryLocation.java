package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Location;
import com.proiect.colectiv.server.Models.Enums.LocationType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RepositoryLocation extends JpaRepository<Location, UUID> {

    /**
     * Save a location
     * @param user_id the id of the user
     * @param entity the location to be saved
     * @return the saved location or empty if the user is not the owner
     */
    @Transactional
    @Modifying
    default Optional<Location> save(UUID user_id, Location entity){
        if (!entity.getUser().getId().equals(user_id)) {
            return Optional.empty();
        }
        return Optional.of(save(entity));
    };

    /**
     * Get all locations of a user
     * @param user_id the id of the user
     * @return a list of locations
     */
    @Query("select l from Location l where l.user.id = ?1")
    List<Location> getLocationsOfUser(UUID user_id);

    /**
     * Get a location of a user by id
     * @param user_id the id of the user
     * @param location_id the id of the location
     * @return the location or empty if the user is not the owner
     */
    @Query("select l from Location l where l.user.id = ?1 and l.id = ?2")
    Optional<Location> getLocationOfUser(UUID user_id, UUID location_id);

    /**
     * Get all locations of a user by type
     * @param user_id the id of the user
     * @param type the type of the location
     * @return a list of locations
     */
    @Query("select l from Location l where l.user.id = ?1 and l.type = ?2")
    List<Location> getLocationsOfUserByType(UUID user_id, LocationType type);

}
