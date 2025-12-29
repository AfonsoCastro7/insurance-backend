package com.mediadortrack.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimulationRequestResponseDTO {
    private Long id;
    private String status;
    private String type;
    private String description;
    private Double value;
    private LocalDateTime createdAt;
    private LeadResponseDTO lead;
}
