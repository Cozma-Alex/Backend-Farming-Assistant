package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Location;
import com.proiect.colectiv.server.Models.LocationType;
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

    @Transactional
    @Modifying
    default Optional<Location> save(UUID user_id, Location entity){
        if (!entity.getUser().getId().equals(user_id)) {
            return Optional.empty();
        }
        return Optional.of(save(entity));
    };

    @Query("select l from Location l where l.user.id = ?1")
    List<Location> getLocationsOfUser(UUID user_id);

    @Query("select l from Location l where l.user.id = ?1 and l.id = ?2")
    Optional<Location> getLocationOfUser(UUID user_id, UUID location_id);

    @Query("select l from Location l where l.user.id = ?1 and l.type = ?2")
    List<Location> getLocationsOfUserByType(UUID user_id, LocationType type);
}
