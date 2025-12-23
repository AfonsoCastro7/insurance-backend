package com.mediadortrack.api.controller;

import com.mediadortrack.api.model.Lead;
import com.mediadortrack.api.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin(origins = "*")
public class LeadController {

    @Autowired
    private LeadRepository leadRepository;

    // GET /api/leads -> Devolve a lista de clientes
    @GetMapping
    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }

    // POST /api/leads -> Cria um cliente novo
    @PostMapping
    public Lead createLead(@RequestBody Lead lead) {
        lead.setCreatedAt(java.time.LocalDateTime.now());
        return leadRepository.save(lead);
    }
}