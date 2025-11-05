package org.arqui.microservicepayment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.arqui.microservicepayment.entity.Rate;
import org.arqui.microservicepayment.mapper.RateMapper;
import org.arqui.microservicepayment.repository.RateRepository;
import org.arqui.microservicepayment.service.DTO.request.RateRequestDTO;
import org.arqui.microservicepayment.service.DTO.response.RateResponseDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RateService {
  private final RateRepository rateRepository;

  @Transactional
  public void save(RateRequestDTO rate) {
    Rate tarifaActualizada = RateMapper.toEntity(rate);
    rateRepository.save(tarifaActualizada);
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
    tarifa.setFecha(rate.getFecha());

    rateRepository.save(tarifa);
    return RateMapper.toResponse(tarifa);
  }

  @Transactional
  public void habilitarNuevosPreciosAPartirDe(LocalDateTime fecha) {
    rateRepository.habilitarNuevosPreciosAPartirDe(fecha);
  }

  @Transactional(readOnly = true)
  public RateResponseDTO findById(@PathVariable Long id) {
    Rate tarifa = rateRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarifa no encontrada con id: " + id));
    return RateMapper.toResponse(tarifa);
  }

  @Transactional(readOnly = true)
  public List<RateResponseDTO> findAll() {
    List<Rate> tarifas = rateRepository.findAll();
    return tarifas.stream().map(RateMapper::toResponse).collect(Collectors.toList());
  }
}
