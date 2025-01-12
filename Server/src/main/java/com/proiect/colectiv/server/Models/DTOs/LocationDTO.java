package com.proiect.colectiv.server.Models.DTOs;

import com.proiect.colectiv.server.Models.Coordinate;
import com.proiect.colectiv.server.Models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Data Transfer Object for Location
 * Contains a Location and a list of Coordinates
 * Used to transfer data between the server and the client
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationDTO {
    private Location location;
    private List<Coordinate> coordinates;
}
