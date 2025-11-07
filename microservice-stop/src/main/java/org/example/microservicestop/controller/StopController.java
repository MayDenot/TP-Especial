package org.example.microservicestop.controller;

import lombok.RequiredArgsConstructor;
import org.example.microservicestop.entity.Stop;
import org.example.microservicestop.service.DTO.Request.StopRequestDTO;
import org.example.microservicestop.service.DTO.Response.StopResponseDTO;
import org.example.microservicestop.service.StopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stops")
@RequiredArgsConstructor
public class StopController {
    private final StopService stopService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody StopRequestDTO stop) {
        try {
            stopService.save(stop);
            return ResponseEntity.status(HttpStatus.CREATED).body("Parada creada con exito");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id ) {
        try {
            stopService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Parada eliminada con exito");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody StopRequestDTO stop) {
        try {
            StopResponseDTO updated = stopService.update(id, stop);
            return ResponseEntity.ok(updated);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
