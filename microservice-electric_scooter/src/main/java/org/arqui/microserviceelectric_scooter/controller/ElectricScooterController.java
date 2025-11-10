package org.arqui.microserviceelectric_scooter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ElectricScooterResponseDTO;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ReporteUsoScooterDTO;
import org.arqui.microserviceelectric_scooter.service.ElectricScooterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scooters")
@RequiredArgsConstructor

public class ElectricScooterController {
    private final ElectricScooterService electricScooterService;




    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ElectricScooterRequestDTO electricScooter) {
        try {
            this.electricScooterService.save(electricScooter);
            return ResponseEntity.status(HttpStatus.CREATED).body("scooter creado con exito");
        } catch (IllegalArgumentException e) {
            // Validaciones de negocio específicas
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Mal formato de monopatin");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor");
        }
    }



    @GetMapping("/latitud/{latitud}/longitud/{longitud}")
    public ResponseEntity<?> obtenerScootersPorLatitudYLongitud(
           @Valid @PathVariable Double latitud,
             @Valid @PathVariable Double longitud) {
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
            @Valid  @PathVariable String id,
            @RequestBody ElectricScooterRequestDTO electricScooterDTO) {
        try {

            electricScooterService.modifier(id, electricScooterDTO);
            return ResponseEntity.status(HttpStatus.OK).body("actualizado con exito ");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se pudo actualizar");
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar( @Valid @PathVariable String id) {
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


    @GetMapping("/reporte")
    public ResponseEntity<List<ReporteUsoScooterDTO>> obtenerMonopatines(
            @RequestParam(defaultValue = "false") boolean incluirPausas) {
        try {
            List<ReporteUsoScooterDTO> reporte = electricScooterService.generarReporteUso(incluirPausas);

            if (reporte.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstadoEnParada(
           @Valid @PathVariable String id,
            @Valid @RequestBody ElectricScooterRequestDTO dto) {
        try {
            ElectricScooterResponseDTO scooter = electricScooterService.actualizarEstadoEnParada(
                    id,
                    dto.getEstado(),
                    dto.getIdParadaActual(),
                    dto.getLatitud(),
                    dto.getLongitud()
            );
            return ResponseEntity.ok(scooter);
        } catch (IllegalArgumentException e) {
            // 400 Bad Request - Errores de validación
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error de validación: " + e.getMessage());
        } catch (RuntimeException e) {
            // 404 Not Found - Scooter no encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            // 500 Internal Server Error - Otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar estado: " + e.getMessage());
        }
    }





}
