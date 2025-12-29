package com.mediadortrack.api.dto;

import lombok.Data;

@Data
public class SimulationRequestRequestDTO {
    private String status;
    private String type;
    private String description;
    private Double value;
    private Long leadId;
}
