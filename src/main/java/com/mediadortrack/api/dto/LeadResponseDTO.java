package com.mediadortrack.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LeadResponseDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String nif;
    private LocalDateTime createdAt;
}
