package org.arqui.microservicetravel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.arqui.microservicetravel.service.DTO.Request.FinalizarViajeRequestDTO;
import org.arqui.microservicetravel.service.DTO.Request.TravelRequestDTO;
import org.arqui.microservicetravel.service.DTO.Response.TravelResponseDTO;
import org.arqui.microservicetravel.service.TravelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/travels")
@RequiredArgsConstructor
@Tag(name = "Viajes", description = "Operaciones relacionadas a viajes")
public class TravelController {
    private final TravelService travelService;

    @Operation(summary = "Crear nuevo viaje")
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

    @Operation(summary = "Eliminar viaje por ID")
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

    @Operation(summary = "Actualizar viaje por ID")
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

    @Operation(summary = "Obtener viaje por ID")
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

    @Operation(summary = "Obtener todos los viajes")
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

    @Operation(summary = "Obtener monopatines por cantidad de viajes en un año")
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

    @Operation(summary = "Obtener usuarios con mas viajes en un periodo")
    @GetMapping("/fechaInicio/{inicio}/fechaFin/{fin}")
    public ResponseEntity<?> buscarUsuariosConMasViajes(@PathVariable LocalDate inicio, @PathVariable LocalDate fin) throws Exception {
        try {
            List <Long> usuarios = travelService.buscarUsuariosConMasViajes(inicio, fin);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtener viajes de un usuario en un periodo")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<?> getViajesPorUsuario(
            @PathVariable Long userId,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
            // Parsear las fechas manualmente
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);

            List<TravelResponseDTO> viajes = travelService.obtenerViajesPorUsuario(userId, inicio, fin);
            return ResponseEntity.ok(viajes);
        } catch (java.time.format.DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Formato de fecha inválido. Use formato yyyy-MM-dd. Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtener viajes con costos")
    @GetMapping("/travelsWithCosts")
    public ResponseEntity<?> getViajesConCostos(
            @RequestParam Integer anio,
            @RequestParam Integer mesInicio,
            @RequestParam Integer mesFin) {
        try {
            List<TravelResponseDTO> viajes = travelService.viajesFinalizados(anio, mesInicio, mesFin);
            return ResponseEntity.ok(viajes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al calcular costos de viajes: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al procesar viajes: " + e.getMessage());
        }
    }

    @Operation(summary = "Finalizar viaje")
    @PutMapping("/finalizar/{id}")
    public ResponseEntity<?> finalizarViaje(
            @PathVariable Long id,
            @RequestBody FinalizarViajeRequestDTO request) {
        try {
            TravelResponseDTO travel = travelService.finalizarViaje(id, request);
            return ResponseEntity.ok(travel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al finalizar viaje: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }

}
