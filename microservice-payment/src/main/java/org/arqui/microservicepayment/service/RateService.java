package org.arqui.microservicepayment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.arqui.microservicepayment.entity.Rate;
import org.arqui.microservicepayment.feignClient.TravelClient;
import org.arqui.microservicepayment.mapper.RateMapper;
import org.arqui.microservicepayment.repository.RateRepository;
import org.arqui.microservicepayment.service.DTO.request.RateRequestDTO;
import org.arqui.microservicepayment.service.DTO.response.BillingResponseDTO;
import org.arqui.microservicepayment.service.DTO.response.RateResponseDTO;
import org.arqui.microservicepayment.service.DTO.response.TravelResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RateService {
  private final RateRepository rateRepository;
  private final TravelClient travelClient;

  @Transactional
  public void save(RateRequestDTO rate) {
    rateRepository.save(RateMapper.toEntity(rate));
  }

  @Transactional
  public void delete(Long id) {
    rateRepository.deleteById(id);
  }

  @Transactional
  public RateResponseDTO update(Long id, RateRequestDTO rate) {
    Rate tarifa = rateRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarifa no encontrada con id: " + id));

    tarifa.setTarifa(rate.getTarifa());
    tarifa.setTarifaExtra(rate.getTarifaExtra());
    tarifa.setFechaInicio(rate.getFechaInicio());

    rateRepository.save(tarifa);
    return RateMapper.toResponse(tarifa);
  }

  @Transactional(readOnly = true)
  public RateResponseDTO findById(Long id) {
    Rate tarifa = rateRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarifa no encontrada con id: " + id));
    return RateMapper.toResponse(tarifa);
  }

  @Transactional(readOnly = true)
  public List<RateResponseDTO> findAll() {
    List<Rate> tarifas = rateRepository.findAll();
    return tarifas.stream().map(RateMapper::toResponse).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public BillingResponseDTO calcularFacturacionPorPeriodo(Integer anio, Integer mesInicio, Integer mesFin) {
    List<TravelResponseDTO> viajes;
    double total = 0.0;

    try {
      viajes = travelClient.getViajesConCostos(anio, mesInicio, mesFin);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Error al obtener viajes para facturación: " + e.getMessage(), e);
    }

    for (TravelResponseDTO viaje : viajes) {
      total += viaje.getCosto();
    }

    return new BillingResponseDTO(
            anio,
            mesInicio,
            mesFin,
            total
    );
  }

  @Transactional
  public RateResponseDTO findRateByDate(LocalDateTime fecha) {
    Rate rate = rateRepository.findRateByDate(fecha).orElseThrow(() ->
      new EntityNotFoundException("No existe tarifa para la fecha solicitada: " + fecha));

    return RateMapper.toResponse(rate);
  }
}
