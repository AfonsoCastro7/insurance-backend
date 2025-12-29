package com.mediadortrack.api.service;

import com.mediadortrack.api.dto.SimulationRequestRequestDTO;
import com.mediadortrack.api.dto.SimulationRequestResponseDTO;
import com.mediadortrack.api.dto.UpdateStatusDTO;

import java.util.List;

public interface SimulationRequestService {
    List<SimulationRequestResponseDTO> getAll();
    SimulationRequestResponseDTO create(SimulationRequestRequestDTO request);
    SimulationRequestResponseDTO update(Long id, SimulationRequestRequestDTO request);
    SimulationRequestResponseDTO updateStatus(Long id, UpdateStatusDTO request);
}
