package com.proiect.colectiv.server.Models;

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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "coordinates", schema = "public")
public class Coordinate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "position")
    private int position;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

}
