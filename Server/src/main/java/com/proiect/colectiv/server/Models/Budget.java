package com.proiect.colectiv.server.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Budget model
 * Represents the budget of the company in the database having the following fields:
 * - id: UUID (primary key)
 * - revenue: double (the money that the company makes)
 * - expenses: double (the money that the company spends)
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "budget", schema = "public")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "revenue")
    private double revenue;

    @Column(name = "expenses")
    private double expenses;

}
