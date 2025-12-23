package com.mediadortrack.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "leads")
@Data
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String nif;
    private String source; // "FACEBOOK", "TELEFONE"

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // RELAÇÃO: Um Cliente tem vários Pedidos
    @OneToMany(mappedBy = "lead")
    @JsonIgnore
    private List<SimulationRequest> requests;
}