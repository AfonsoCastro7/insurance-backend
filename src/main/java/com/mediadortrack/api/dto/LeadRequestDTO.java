package com.mediadortrack.api.dto;

import lombok.Data;

@Data
public class LeadRequestDTO {
    private String name;
    private String phone;
    private String email;
    private String nif;
}
