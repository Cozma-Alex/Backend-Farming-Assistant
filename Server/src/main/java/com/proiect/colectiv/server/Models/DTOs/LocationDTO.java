package com.proiect.colectiv.server.Models.DTOs;

import com.proiect.colectiv.server.Models.Coordinate;
import com.proiect.colectiv.server.Models.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for Location
 * Contains a Location and a list of Coordinates
 * Used to transfer data between the server and the client
 */
@Schema(description = "Data Transfer Object for Location with its coordinates")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationDTO {
    @Schema(description = "Location details")
    private Location location;

    @Schema(description = "List of coordinates defining the location boundary")
    private List<Coordinate> coordinates;
}
