package org.arqui.microservicepayment.controller;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.arqui.microservicepayment.service.DTO.request.RateRequestDTO;
import org.arqui.microservicepayment.service.DTO.response.BillingResponseDTO;
import org.arqui.microservicepayment.service.DTO.response.RateResponseDTO;
import org.arqui.microservicepayment.service.RateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
@Tag(name = "Tarifas", description = "Operaciones relacionadas a tarifas")
public class RateController {
  private final RateService rateService;

  @Operation(summary = "Crear nueva tarifa")
  @PostMapping
  public ResponseEntity<?> save(@RequestBody RateRequestDTO rateDTO) throws Exception {
    try {
      rateService.save(rateDTO);
      return ResponseEntity.status(201).body("Tarifa creada con exito");
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
    try {
      rateService.delete(id);
      return ResponseEntity.status(204).body("Tarifa eliminada con exito");
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RateRequestDTO rateDTO) throws Exception {
    try {
      rateService.update(id, rateDTO);
      return ResponseEntity.status(200).body("Tarifa actualizada con exito");
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Operation(summary = "Obtener tarifa por ID",
     parameters = {
         @Parameter(
             name = "id",
             description = "ID de la tarifa",
             in = ParameterIn.PATH,
             example = "1"
         )
     }
  )
  @GetMapping("/{id}")
  public ResponseEntity<RateResponseDTO> findById(@PathVariable Long id) throws Exception {
    try {
      return ResponseEntity.ok(rateService.findById(id));
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Operation(
          summary = "Obtener todas las tarifas",
          description = "Retorna una lista de todas las tarifas disponibles"
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa"),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @GetMapping()
  public ResponseEntity<List<?>> findAll() throws Exception {
    try {
      return ResponseEntity.ok(rateService.findAll());
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Operation(
          summary = "Obtener la facturacion dado un año, mes inicio y mes fin",
          description = "Retorna una facturacion"
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa"),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @GetMapping("/billing")
  public ResponseEntity<BillingResponseDTO> obtenerFacturacionPorPeriodo(
          @RequestParam Integer anio,
          @RequestParam Integer mesInicio,
          @RequestParam Integer mesFin) throws Exception {
    try {
      BillingResponseDTO facturacion =
              rateService.calcularFacturacionPorPeriodo(anio, mesInicio, mesFin);
      return ResponseEntity.ok(facturacion);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Operation(
          summary = "Obtener la tarifa mas reciente dada una fecha de viaje",
          description = "Retorna una tarifa"
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa"),
          @ApiResponse(responseCode = "404", description = "No se encontro tarifa para la fecha solicitada"),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  // Traer tarifa segun fecha de viaje
  @GetMapping("/byDate")
  public ResponseEntity<?> getRateByDate(@RequestParam(required = false) String fecha) {
    try {
      LocalDateTime fechaConsulta;
      if (fecha != null && !fecha.isEmpty()) {
        // Añadir segundos si no están presentes (formato: 2024-09-15T08:30)
        if (fecha.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}")) {
          fecha = fecha + ":00";
        }
        fechaConsulta = LocalDateTime.parse(fecha);
      } else {
        fechaConsulta = LocalDateTime.now();
      }

      RateResponseDTO rate = rateService.findRateByDate(fechaConsulta);
      return ResponseEntity.ok(rate);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(404).body("No se encontró tarifa para la fecha solicitada: " + e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500).body("Error al procesar fecha: " + e.getMessage());
    }
  }
}
