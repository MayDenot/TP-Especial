package org.arqui.microservicetravel.controller;

import lombok.RequiredArgsConstructor;
import org.arqui.microservicetravel.service.DTO.Request.TravelRequestDTO;
import org.arqui.microservicetravel.service.DTO.Response.TravelResponseDTO;
import org.arqui.microservicetravel.service.TravelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/travels")
@RequiredArgsConstructor
public class TravelController {
    private TravelService travelService;

    @PostMapping
    public ResponseEntity<String> saveTravel(@RequestBody TravelRequestDTO travel) {
        travelService.save(travel);
        return ResponseEntity.status(HttpStatus.CREATED).body("Viaje creado con exito");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTravel(@PathVariable Long id) {
        travelService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Viaje eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTravel(@PathVariable Long id, @RequestBody TravelRequestDTO travel) {
        travelService.update(id, travel);
        return ResponseEntity.status(HttpStatus.OK).body("Viaje actualizado con exito");
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelResponseDTO> getTravel(@RequestParam Long id) {
        TravelResponseDTO travel = travelService.findById(id);
        return ResponseEntity.ok(travel);
    }

    @GetMapping
    public ResponseEntity<List<TravelResponseDTO>> getAllTravels() {
        List<TravelResponseDTO> travels = travelService.findAll();
        return ResponseEntity.ok(travels);
    }

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
