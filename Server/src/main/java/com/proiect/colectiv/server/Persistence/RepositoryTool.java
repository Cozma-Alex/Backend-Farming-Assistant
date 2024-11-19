package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositoryTool extends JpaRepository<Tool, UUID> {
}
