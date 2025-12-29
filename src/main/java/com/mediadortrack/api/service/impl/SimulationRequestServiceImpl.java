package com.mediadortrack.api.service.impl;

import com.mediadortrack.api.dto.LeadResponseDTO;
import com.mediadortrack.api.dto.SimulationRequestRequestDTO;
import com.mediadortrack.api.dto.SimulationRequestResponseDTO;
import com.mediadortrack.api.dto.UpdateStatusDTO;
import com.mediadortrack.api.model.Lead;
import com.mediadortrack.api.model.SimulationRequest;
import com.mediadortrack.api.repository.LeadRepository;
import com.mediadortrack.api.repository.SimulationRequestRepository;
import com.mediadortrack.api.service.SimulationRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class SimulationRequestServiceImpl implements SimulationRequestService {

    private static final Set<String> ALLOWED_STATUSES = Set.of(
            "EM_ANALISE",
            "NOVO",
            "GANHO",
            "AGUARDA_CLIENTE",
            "PERDIDO"
    );

    private final SimulationRequestRepository simulationRepository;
    private final LeadRepository leadRepository;

    public SimulationRequestServiceImpl(SimulationRequestRepository simulationRepository,
                                        LeadRepository leadRepository) {
        this.simulationRepository = simulationRepository;
        this.leadRepository = leadRepository;
    }

    @Override
    public List<SimulationRequestResponseDTO> getAll() {
        return simulationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public SimulationRequestResponseDTO create(SimulationRequestRequestDTO request) {
        Lead lead = findLead(request.getLeadId());
        SimulationRequest simulation = new SimulationRequest();
        simulation.setLead(lead);
        simulation.setStatus(normalizeStatus(request.getStatus()));
        simulation.setType(request.getType());
        simulation.setDescription(request.getDescription());
        simulation.setValue(request.getValue());
        simulation.setCreatedAt(LocalDateTime.now());

        return toResponse(simulationRepository.save(simulation));
    }

    @Override
    public SimulationRequestResponseDTO update(Long id, SimulationRequestRequestDTO request) {
        SimulationRequest simulation = findSimulation(id);
        Lead lead = findLead(request.getLeadId());

        simulation.setLead(lead);
        simulation.setStatus(normalizeStatus(request.getStatus()));
        simulation.setType(request.getType());
        simulation.setDescription(request.getDescription());
        simulation.setValue(request.getValue());
        simulation.setUpdatedAt(LocalDateTime.now());

        return toResponse(simulationRepository.save(simulation));
    }

    @Override
    public SimulationRequestResponseDTO updateStatus(Long id, UpdateStatusDTO request) {
        SimulationRequest simulation = findSimulation(id);
        simulation.setStatus(normalizeStatus(request.getStatus()));
        simulation.setUpdatedAt(LocalDateTime.now());
        return toResponse(simulationRepository.save(simulation));
    }

    private SimulationRequest findSimulation(Long id) {
        return simulationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Simulation not found"));
    }

    private Lead findLead(Long leadId) {
        if (leadId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lead id is required");
        }
        return leadRepository.findById(leadId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lead not found"));
    }

    private String normalizeStatus(String status) {
        if (status == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is required");
        }
        String normalized = status.trim().toUpperCase();
        if (!ALLOWED_STATUSES.contains(normalized)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status");
        }
        return normalized;
    }

    private SimulationRequestResponseDTO toResponse(SimulationRequest simulation) {
        SimulationRequestResponseDTO dto = new SimulationRequestResponseDTO();
        dto.setId(simulation.getId());
        dto.setStatus(simulation.getStatus());
        dto.setType(simulation.getType());
        dto.setDescription(simulation.getDescription());
        dto.setValue(simulation.getValue());
        dto.setCreatedAt(simulation.getCreatedAt());
        if (simulation.getLead() != null) {
            dto.setLead(toLeadResponse(simulation.getLead()));
        }
        return dto;
    }

    private LeadResponseDTO toLeadResponse(Lead lead) {
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
