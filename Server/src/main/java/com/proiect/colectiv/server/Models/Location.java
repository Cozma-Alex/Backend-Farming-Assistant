package com.proiect.colectiv.server.Models;

import com.proiect.colectiv.server.Models.Enums.LocationType;
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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "locations", schema = "public")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LocationType type;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
