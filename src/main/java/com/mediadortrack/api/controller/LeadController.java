package com.mediadortrack.api.controller;

import com.mediadortrack.api.dto.LeadRequestDTO;
import com.mediadortrack.api.dto.LeadResponseDTO;
import com.mediadortrack.api.service.LeadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leads")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @GetMapping
    public List<LeadResponseDTO> getAllLeads() {
        return leadService.getAll();
    }


    @PostMapping
    public LeadResponseDTO createLead(@RequestBody LeadRequestDTO lead) {
        return leadService.create(lead);
    }
}
