package org.arqui.microservicetravel.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.arqui.microservicetravel.service.TravelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/travel")
@RequiredArgsConstructor
public class TravelController {
    private TravelService travelService;

    @GetMapping("/cantidad/{cantidad}/anio/{anio}")
    public ResponseEntity<?> buscarPorCantidadDeViajesYAño(@RequestBody Integer cantidad, @RequestBody Integer anio) throws Exception {
        try {
             List<String> monopatines = travelService.buscarPorCantidadDeViajesYAño(cantidad, anio);
             return ResponseEntity.ok(monopatines);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
}
