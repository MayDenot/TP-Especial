package org.arqui.microserviceelectric_scooter.controller;

import lombok.RequiredArgsConstructor;
import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.arqui.microserviceelectric_scooter.service.ElectricScooterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scooter")
@RequiredArgsConstructor

public class ElectricScooterController {
    private ElectricScooterService electricScooterService;





    public ElectricScooter save(ElectricScooter electricScooter){

        return electricScooter;
    }
}
