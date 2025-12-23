package com.mediadortrack.api.repository;

import com.mediadortrack.api.model.SimulationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRequestRepository extends JpaRepository<SimulationRequest, Long> {
}