package org.arqui.microservicetravel.controller;

import lombok.RequiredArgsConstructor;
import org.arqui.microservicetravel.service.DTO.Request.TravelRequestDTO;
import org.arqui.microservicetravel.service.DTO.Response.TravelResponseDTO;
import org.arqui.microservicetravel.service.TravelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.arqui.microservicetravel.service.DTO.Response.ViajeConCostoResponseDTO;

@RestController
@RequestMapping("/api/travels")
@RequiredArgsConstructor
public class TravelController {
    private final TravelService travelService;

    @PostMapping
    public ResponseEntity<String> saveTravel(@RequestBody TravelRequestDTO travel) {
        try {
            travelService.save(travel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Viaje creado con exito");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTravel(@PathVariable Long id) {
        try {
            travelService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Viaje eliminado con exito");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTravel(@PathVariable Long id, @RequestBody TravelRequestDTO travel) {
        try {
            travelService.update(id, travel);
            return ResponseEntity.status(HttpStatus.OK).body("Viaje actualizado con exito");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTravel(@PathVariable Long id) {
        try{
            TravelResponseDTO travel = travelService.findById(id);
            return ResponseEntity.ok(travel);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTravels() {
        try {
            List<TravelResponseDTO> travels = travelService.findAll();
            return ResponseEntity.ok(travels);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }

    }

    @GetMapping("/cantidad/{cantidad}/anio/{anio}")
    public ResponseEntity<?> buscarPorCantidadDeViajesYAño(@PathVariable Integer cantidad, @PathVariable Integer anio) throws Exception {
        try {
             List<String> monopatines = travelService.buscarPorCantidadDeViajesYAño(cantidad, anio);
             return ResponseEntity.ok(monopatines);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/fechaInicio/{inicio}/fechaFin/{fin}")
    public ResponseEntity<?> buscarUsuariosConMasViajes(@PathVariable LocalDate inicio, @PathVariable LocalDate fin) throws Exception {
        try {
            List <Long> usuarios = travelService.buscarUsuariosConMasViajes(inicio, fin);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/rates/startDate/{inicio}/endDate/{fin}")
    public ResponseEntity<?> buscarTarifas(@PathVariable LocalDateTime inicio, @PathVariable LocalDateTime fin) throws Exception {
        try {
            List <Long> tarifas = travelService.buscarTarifas(inicio, fin);
            return ResponseEntity.ok(tarifas);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/viajes-con-costos")
    public ResponseEntity<List<ViajeConCostoResponseDTO>> getViajesConCostos(
            @RequestParam Integer anio,
            @RequestParam Integer mesInicio,
            @RequestParam Integer mesFin) {
        
        List<ViajeConCostoResponseDTO> viajes = travelService.calcularCostosDeViajes(anio, mesInicio, mesFin);
        return ResponseEntity.ok(viajes);
    }
}
