package org.arqui.microservicemaintenance.controller;

import lombok.RequiredArgsConstructor;
import org.arqui.microservicemaintenance.service.MaintenanceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {
  private MaintenanceService maintenanceService;

}
