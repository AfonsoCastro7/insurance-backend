package com.mediadortrack.api.controller;

import com.mediadortrack.api.dto.SimulationRequestRequestDTO;
import com.mediadortrack.api.dto.SimulationRequestResponseDTO;
import com.mediadortrack.api.dto.UpdateStatusDTO;
import com.mediadortrack.api.service.SimulationRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/simulations")
public class SimulationRequestController {

    private final SimulationRequestService simulationService;

    public SimulationRequestController(SimulationRequestService simulationService) {
        this.simulationService = simulationService;
    }

    @GetMapping
    public List<SimulationRequestResponseDTO> getAllSimulations() {
        return simulationService.getAll();
    }

    @PostMapping
    public SimulationRequestResponseDTO createSimulation(@RequestBody SimulationRequestRequestDTO request) {
        return simulationService.create(request);
    }

    @PutMapping("/{id}")
    public SimulationRequestResponseDTO updateSimulation(@PathVariable Long id,
                                                         @RequestBody SimulationRequestRequestDTO request) {
        return simulationService.update(id, request);
    }

    @PutMapping("/{id}/status")
    public SimulationRequestResponseDTO updateStatus(@PathVariable Long id,
                                                     @RequestBody UpdateStatusDTO request) {
        return simulationService.updateStatus(id, request);
    }
}
