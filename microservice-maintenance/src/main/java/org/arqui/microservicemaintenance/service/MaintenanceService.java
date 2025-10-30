package org.arqui.microservicemaintenance.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microservicemaintenance.repository.MaintenanceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceService {
  private MaintenanceRepository maintenanceRepository;
}
