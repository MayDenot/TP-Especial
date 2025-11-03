package org.arqui.microservicepayment.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.arqui.microservicepayment.entity.Rate;
import org.arqui.microservicepayment.mapper.RateMapper;
import org.arqui.microservicepayment.repository.RateRepository;
import org.arqui.microservicepayment.service.DTO.request.RateRequestDTO;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RateService {
  private RateRepository rateRepository;

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
  public void update(Long id, RateRequestDTO rate) {
    Rate tarifa = rateRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarifa no encontrada con id: " + id));

    tarifa.setTarifa(rate.getTarifa());
    tarifa.setTarifaExtra(rate.getTarifaExtra());
    tarifa.setFecha(rate.getFecha());

    rateRepository.save(tarifa);
  }

  @Transactional
  public void habilitarNuevosPreciosAPartirDe(LocalDateTime fecha) {
    rateRepository.habilitarNuevosPreciosAPartirDe(fecha);
  }
}
