package com.mediadortrack.api.service.impl;

import com.mediadortrack.api.dto.LeadRequestDTO;
import com.mediadortrack.api.dto.LeadResponseDTO;
import com.mediadortrack.api.model.Lead;
import com.mediadortrack.api.repository.LeadRepository;
import com.mediadortrack.api.service.LeadService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;

    public LeadServiceImpl(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Override
    public List<LeadResponseDTO> getAll() {
        return leadRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public LeadResponseDTO create(LeadRequestDTO request) {
        Lead lead = new Lead();
        lead.setName(request.getName());
        lead.setEmail(request.getEmail());
        lead.setNif(request.getNif());
        lead.setPhone(request.getPhone());
        lead.setCreatedAt(LocalDateTime.now());

        return toResponse(leadRepository.save(lead));
    }

    private LeadResponseDTO toResponse(Lead lead) {
        LeadResponseDTO dto = new LeadResponseDTO();
        dto.setId(lead.getId());
        dto.setName(lead.getName());
        dto.setEmail(lead.getEmail());
        dto.setNif(lead.getNif());
        dto.setPhone(lead.getPhone());
        dto.setCreatedAt(lead.getCreatedAt());
        return dto;
    }
}
