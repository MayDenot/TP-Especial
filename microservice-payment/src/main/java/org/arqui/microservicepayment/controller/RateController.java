package org.arqui.microservicepayment.controller;

import lombok.RequiredArgsConstructor;
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

  @PutMapping("/enable/{fecha}")
  public ResponseEntity<?> habilitarNuevosPreciosAPartirDe(@PathVariable LocalDateTime fecha) throws Exception {
    try {
      rateService.habilitarNuevosPreciosAPartirDe(fecha);
      return ResponseEntity.status(200).body("Tarifa habilitada con exito");
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

  @GetMapping("/current")
    public ResponseEntity<RateResponseDTO> getRateByDate(@PathVariable LocalDateTime fecha) throws Exception {
      try {
        return ResponseEntity.ok(rateService.findRateByDate(fecha));
      } catch (Exception e) {
        throw new Exception(e.getMessage());
      }
    }

    @GetMapping("/billing")
    public ResponseEntity<BillingResponseDTO> obtenerFacturacionPorPeriodo(
            @PathVariable Integer anio,
            @PathVariable Integer mesInicio,
            @PathVariable Integer mesFin) throws Exception {
      try {
        BillingResponseDTO facturacion =
                rateService.calcularFacturacionPorPeriodo(anio, mesInicio, mesFin);
        return ResponseEntity.ok(facturacion);
      } catch (Exception e) {
        throw new Exception(e.getMessage());
      }
    }

}
