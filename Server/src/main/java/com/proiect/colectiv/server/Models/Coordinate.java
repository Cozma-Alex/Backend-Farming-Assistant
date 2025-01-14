package com.proiect.colectiv.server.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Coordinate model
 * Represents a coordinate in the database having the following fields:
 * - id: UUID (unique identifier of the coordinate) [PK]
 * - latitude: double (latitude of the coordinate)
 * - longitude: double (longitude of the coordinate)
 * - position: int (position of the coordinate in the location)
 * - location: Location (location of the coordinate)
 */
@Schema(description = "Coordinate entity representing a point in a location")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "coordinates", schema = "public")
public class Coordinate {

    @Schema(description = "Unique identifier of the coordinate", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Schema(description = "Latitude of the coordinate")
    @Column(name = "latitude")
    private double latitude;

    @Schema(description = "Longitude of the coordinate")
    @Column(name = "longitude")
    private double longitude;

    @Schema(description = "Position of the coordinate in the location boundary")
    @Column(name = "position")
    private int position;

    @Schema(description = "Location this coordinate belongs to")
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

}
