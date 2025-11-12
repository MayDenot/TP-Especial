package org.arqui.microservicepayment.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.arqui.microservicepayment.entity.Rate;
import org.arqui.microservicepayment.service.DTO.request.RateRequestDTO;
import org.arqui.microservicepayment.service.DTO.response.BillingResponseDTO;
import org.arqui.microservicepayment.service.DTO.response.RateResponseDTO;
import org.arqui.microservicepayment.service.RateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class RateController {
  private final RateService rateService;

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

  @GetMapping("/{id}")
  public ResponseEntity<RateResponseDTO> findById(@PathVariable Long id) throws Exception {
    try {
      return ResponseEntity.ok(rateService.findById(id));
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @GetMapping()
  public ResponseEntity<List<?>> findAll() throws Exception {
    try {
      return ResponseEntity.ok(rateService.findAll());
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

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

      System.out.println("Buscando tarifa para fecha: " + fechaConsulta);
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
