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
    public ResponseEntity<List<ElectricScooterResponseDTO>> obtenerScootersPorLatitudYLongitud(
            @PathVariable Double latitud,
            @PathVariable Double longitud) {
        try {
            List<ElectricScooterResponseDTO> scooters = electricScooterService.obtenerCercanos(latitud, longitud);

            if (scooters.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
                // o ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
            }

            return ResponseEntity.ok(scooters);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
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
