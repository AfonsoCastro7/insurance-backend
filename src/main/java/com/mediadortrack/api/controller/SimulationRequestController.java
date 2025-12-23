package com.mediadortrack.api.controller;

import com.mediadortrack.api.model.SimulationRequest;
import com.mediadortrack.api.repository.SimulationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/simulations")
@CrossOrigin(origins = "*")
public class SimulationRequestController {

    @Autowired
    private SimulationRequestRepository simulationRepository;

    @GetMapping
    public List<SimulationRequest> getAllSimulations() {
        return simulationRepository.findAll();
    }


    @PostMapping
    public SimulationRequest createSimulation(@RequestBody SimulationRequest request) {
        request.setCreatedAt(java.time.LocalDateTime.now());
        return simulationRepository.save(request);
    }

    @PutMapping("/{id}/status")
    public SimulationRequest updateStatus(@PathVariable Long id, @RequestBody String newStatus) {
        return simulationRepository.findById(id).map(request -> {
            request.setStatus(newStatus.replace("\"", "")); // Limpa aspas extra se vierem
            request.setUpdatedAt(java.time.LocalDateTime.now());
            return simulationRepository.save(request);
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));
    }
}