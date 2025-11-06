package org.arqui.microserviceelectric_scooter.controller;

import lombok.RequiredArgsConstructor;
import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ElectricScooterResponseDTO;
import org.arqui.microserviceelectric_scooter.service.ElectricScooterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scooters")
@RequiredArgsConstructor

public class ElectricScooterController {
    private final ElectricScooterService electricScooterService;




    @PostMapping
    public ResponseEntity<?> save(@RequestBody ElectricScooterRequestDTO electricScooter) {

        try {
            this.electricScooterService.save(electricScooter);
            return ResponseEntity.status(HttpStatus.CREATED).body("scooter creado con exito");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @GetMapping("/latitud/{latitud}/longitud/{longitud}")
    public ResponseEntity<?> obtenerScootersPorLatitudYLongitud(
            @PathVariable Double latitud,
            @PathVariable Double longitud) {
        try {
            List<ElectricScooterResponseDTO> scooters =
                    electricScooterService.obtenerCercanos(latitud, longitud);

            if (scooters.isEmpty()) {
                // Retornar 200 OK con mensaje informativo (mejor UX)
                return ResponseEntity.ok(Map.of(
                        "mensaje", "No hay monopatines disponibles cerca de tu ubicación",
                        "scooters", List.of(),
                        "radio_km", 2.0
                ));
                // O si prefieres 204 No Content:
                // return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(scooters);

        } catch (Exception e) {
            System.err.println("❌ Error en obtenerScootersPorLatitudYLongitud: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> modifier(
            @PathVariable String id,
            @RequestBody ElectricScooterRequestDTO electricScooterDTO) {
        try {

            electricScooterService.modifier(id, electricScooterDTO);
            return ResponseEntity.status(HttpStatus.OK).body("actualizado con exito ");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se pudo actualizar");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        try {
            this.electricScooterService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("eliminado con exito");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se pudo eliminar");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectricScooterResponseDTO> getById(@PathVariable String id) {
        ElectricScooterResponseDTO scooter = this.electricScooterService.getById(id);
        return ResponseEntity.ok(scooter); // 200 OK
    }


    @GetMapping("/cantidad/{cantidad}/anio/{anio}")
    public ResponseEntity<List<ElectricScooterResponseDTO>> obtenerMonopatinesDeTravel(@PathVariable Integer cantidad, @PathVariable Integer anio) {
        List<ElectricScooterResponseDTO> monopatines = this.electricScooterService.obtenerMonopatinesByAnioYCantidad(cantidad, anio);
        return ResponseEntity.ok(monopatines);
    }







}
