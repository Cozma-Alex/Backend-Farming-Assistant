package com.proiect.colectiv.server.Models;

import com.proiect.colectiv.server.Models.Enums.LocationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Location model
 * Represents a location in the database with the following fields:
 * - id: UUID - the id of the location (primary key)
 * - type: LocationType - the type of the location (enum)
 * - name: String - the name of the location
 * - user: User - the user that owns the location (foreign key)
 */
@Schema(description = "Location entity representing a farm location")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "locations", schema = "public")
public class Location {

    @Schema(description = "Unique identifier of the location", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Schema(description = "Type of the location (FIELD, BARN, STORAGE, TOOLS)")
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LocationType type;

    @Schema(description = "Name of the location")
    @Column(name = "name")
    private String name;

    @Schema(description = "User who owns the location")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
