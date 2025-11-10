package org.arqui.microservicepayment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.arqui.microservicepayment.entity.Rate;
import org.arqui.microservicepayment.feignClient.TravelClient;
import org.arqui.microservicepayment.mapper.RateMapper;
import org.arqui.microservicepayment.repository.RateRepository;
import org.arqui.microservicepayment.service.DTO.request.RateRequestDTO;
import org.arqui.microservicepayment.service.DTO.request.TravelWithCostRequestDTO;
import org.arqui.microservicepayment.service.DTO.response.BillingResponseDTO;
import org.arqui.microservicepayment.service.DTO.response.RateResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RateService {
  private final RateRepository rateRepository;
  private final TravelClient travelClient;

  @Transactional
  public void save(RateRequestDTO rate) {
    Rate tarifaActualizada = RateMapper.toEntity(rate);
    tarifaActualizada.setVigente(false);
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
    tarifa.setFechaActualizacion(rate.getFechaActualizacion());

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
    List<TravelWithCostRequestDTO> viajes = travelClient.getViajesConCostos(anio, mesInicio, mesFin);

    double totalFacturado = 0.0;
    double totalTarifasBase = 0.0;
    double totalTarifasExtra = 0.0;
    double totalDescuentosPremium = 0.0;

    Map<String, Integer> kmPorUsuarioPorMes = new HashMap<>();

    for (TravelWithCostRequestDTO viaje : viajes) {
      double costoFinal = viaje.getCostoTotal();

      totalTarifasBase += viaje.getCostoBase();
      totalTarifasExtra += viaje.getCostoExtra();

      if ("PREMIUM".equalsIgnoreCase(viaje.getTipoCuenta())) {
        String keyUsuarioMes = viaje.getUsuario() + "-" +
                viaje.getFecha_hora_inicio().getYear() + "-" +
                viaje.getFecha_hora_inicio().getMonthValue();

        int kmAcumulados = kmPorUsuarioPorMes.getOrDefault(keyUsuarioMes, 0);
        int kmViaje = viaje.getKmRecorridos() != null ? viaje.getKmRecorridos() : 0;

        if (kmAcumulados >= 100) {
          double descuento = costoFinal * 0.5;
          costoFinal -= descuento;
          totalDescuentosPremium += descuento;
        } else if (kmAcumulados + kmViaje > 100) {
          int kmConDescuento = (kmAcumulados + kmViaje) - 100;
          double proporcionDescuento = (double) kmConDescuento / kmViaje;
          double descuento = costoFinal * proporcionDescuento * 0.5;
          costoFinal -= descuento;
          totalDescuentosPremium += descuento;
        }

        kmPorUsuarioPorMes.put(keyUsuarioMes, kmAcumulados + kmViaje);
      }

      totalFacturado += costoFinal;
    }

    return new BillingResponseDTO(
            anio,
            mesInicio,
            mesFin,
            totalFacturado,
            viajes.size(),
            totalTarifasBase,
            totalTarifasExtra,
            totalDescuentosPremium
    );
  }

  @Transactional(readOnly = true)
  public RateResponseDTO findRateByDate(LocalDateTime fecha) {
    Rate rate = rateRepository.findRateByDate(fecha)
            .orElseThrow(() -> new EntityNotFoundException("No se encontró tarifa vigente para la fecha: " + fecha));
    return RateMapper.toResponse(rate);
  }
}
