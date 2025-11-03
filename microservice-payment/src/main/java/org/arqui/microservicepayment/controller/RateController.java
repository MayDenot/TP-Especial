package org.arqui.microservicepayment.controller;

import lombok.RequiredArgsConstructor;
import org.arqui.microservicepayment.service.DTO.request.RateRequestDTO;
import org.arqui.microservicepayment.service.RateService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class RateController {
  private RateService rateService;

  @PostMapping
  public void save(@RequestBody RateRequestDTO rateDTO) throws Exception {
    try {
      rateService.save(rateDTO);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @DeleteMapping("/rate/{id}")
  public void delete(@PathVariable Long id) throws Exception {
    try {
      rateService.delete(id);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @PutMapping("/rate/{id}")
  public void update(@PathVariable Long id, @RequestBody RateRequestDTO rateDTO) throws Exception {
    try {
      rateService.update(id, rateDTO);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @PutMapping("/rate/enable/{fecha}")
  public void habilitarNuevosPreciosAPartirDe(@RequestParam LocalDateTime fecha) throws Exception {
    try {
      rateService.habilitarNuevosPreciosAPartirDe(fecha);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}
