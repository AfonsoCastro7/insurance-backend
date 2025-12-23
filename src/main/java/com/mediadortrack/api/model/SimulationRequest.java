package com.mediadortrack.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "simulation_requests")
@Data
public class SimulationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // "NOVO", "GANHO", etc.
    private String type;   // "Auto", "Saúde"

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double value;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // RELAÇÃO: Muitos Pedidos pertencem a uma Lead
    @ManyToOne
    @JoinColumn(name = "lead_id")
    private Lead lead;
}