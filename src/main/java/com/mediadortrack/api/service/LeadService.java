package com.mediadortrack.api.service;

import com.mediadortrack.api.dto.LeadRequestDTO;
import com.mediadortrack.api.dto.LeadResponseDTO;

import java.util.List;

public interface LeadService {
    List<LeadResponseDTO> getAll();
    LeadResponseDTO create(LeadRequestDTO request);
}
