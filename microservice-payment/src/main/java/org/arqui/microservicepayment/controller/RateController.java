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

  @Operation(summary = "Crear nueva tarifa", description = "Crea una nueva tarifa en el sistema")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Tarifa creada exitosamente"),
          @ApiResponse(responseCode = "500", description = "Error al crear la tarifa")
  })
  @PostMapping
  public ResponseEntity<?> save(@RequestBody RateRequestDTO rateDTO) throws Exception {
    try {
      rateService.save(rateDTO);
      return ResponseEntity.status(201).body("Tarifa creada con exito");
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Operation(summary = "Eliminar tarifa", description = "Elimina una tarifa existente por su ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Tarifa eliminada exitosamente"),
          @ApiResponse(responseCode = "404", description = "Tarifa no encontrada"),
          @ApiResponse(responseCode = "500", description = "Error al eliminar la tarifa")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
    try {
      rateService.delete(id);
      return ResponseEntity.status(204).body("Tarifa eliminada con exito");
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Operation(summary = "Actualizar tarifa", description = "Actualiza una tarifa existente")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Tarifa actualizada exitosamente"),
          @ApiResponse(responseCode = "404", description = "Tarifa no encontrada"),
          @ApiResponse(responseCode = "500", description = "Error al actualizar la tarifa")
  })
  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RateRequestDTO rateDTO) throws Exception {
    try {
      rateService.update(id, rateDTO);
      return ResponseEntity.status(200).body("Tarifa actualizada con exito");
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Operation(summary = "Obtener tarifa por ID", description = "Busca y retorna una tarifa específica por su ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Tarifa encontrada"),
          @ApiResponse(responseCode = "404", description = "Tarifa no encontrada"),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @GetMapping("/{id}")
  public ResponseEntity<RateResponseDTO> findById(
          @Parameter(description = "ID de la tarifa", example = "1")
          @PathVariable Long id) throws Exception {
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
          summary = "Obtener facturación por período",
          description = "Calcula y retorna la facturación total para un período específico"
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Facturación calculada exitosamente"),
          @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @GetMapping("/billing")
  public ResponseEntity<BillingResponseDTO> obtenerFacturacionPorPeriodo(
          @Parameter(description = "Año de consulta", example = "2024")
          @RequestParam Integer anio,
          @Parameter(description = "Mes de inicio (1-12)", example = "1")
          @RequestParam Integer mesInicio,
          @Parameter(description = "Mes de fin (1-12)", example = "12")
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
          summary = "Obtener tarifa por fecha de viaje",
          description = "Retorna la tarifa vigente para una fecha específica de viaje. Si no se proporciona fecha, usa la fecha actual."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Tarifa encontrada"),
          @ApiResponse(responseCode = "404", description = "No se encontró tarifa para la fecha solicitada"),
          @ApiResponse(responseCode = "500", description = "Error al procesar la fecha")
  })
  @GetMapping("/byDate")
  public ResponseEntity<?> getRateByDate(
          @Parameter(
                  description = "Fecha de viaje en formato ISO (yyyy-MM-dd'T'HH:mm:ss). Si no se proporciona, usa la fecha actual",
                  example = "2024-09-15T08:30:00"
          )
          @RequestParam(required = false) String fecha) {
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
