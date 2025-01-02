package com.proiect.colectiv.server.Models.DTOs;

import com.proiect.colectiv.server.Models.Coordinate;
import com.proiect.colectiv.server.Models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationDTO {
    private Location location;
    private List<Coordinate> coordinates;
}
