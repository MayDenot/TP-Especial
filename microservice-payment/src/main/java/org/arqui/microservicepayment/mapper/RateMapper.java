package org.arqui.microservicepayment.mapper;

import org.arqui.microservicepayment.entity.Rate;
import org.arqui.microservicepayment.service.DTO.request.RateRequestDTO;
import org.arqui.microservicepayment.service.DTO.response.RateResponseDTO;

public class RateMapper {

  public static Rate toEntity(RateRequestDTO dto) {
    Rate rate = new Rate();
    rate.setTarifa(dto.getTarifa());
    rate.setTarifaExtra(dto.getTarifaExtra());
    rate.setFecha(dto.getFecha());
    return rate;
  }

  public static RateResponseDTO toResponse(Rate rate) {
    return new RateResponseDTO(
            rate.getId(),
            rate.getTarifa(),
            rate.getTarifaExtra(),
            rate.getFecha()
    );
  }
}
