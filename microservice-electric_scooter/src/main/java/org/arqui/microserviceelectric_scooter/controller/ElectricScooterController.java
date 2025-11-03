package org.arqui.microserviceelectric_scooter.controller;

import lombok.RequiredArgsConstructor;
import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;
import org.arqui.microserviceelectric_scooter.service.ElectricScooterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scooter")
@RequiredArgsConstructor

public class ElectricScooterController {
    private ElectricScooterService electricScooterService;


    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ElectricScooterRequestDTO electricScooter) {

        try {
            this.electricScooterService.save(electricScooter);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


    }
}
