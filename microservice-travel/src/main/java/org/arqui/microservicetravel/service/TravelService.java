package org.arqui.microservicetravel.service;

import lombok.RequiredArgsConstructor;
import org.arqui.microservicetravel.Mapper.TravelMapper;
import org.arqui.microservicetravel.entity.Travel;
import org.arqui.microservicetravel.feignClient.AccountClient;
import org.arqui.microservicetravel.feignClient.ElectricScooterClient;
import org.arqui.microservicetravel.feignClient.RateClient;
import org.arqui.microservicetravel.repository.TravelRepository;
import org.arqui.microservicetravel.service.DTO.Request.ActualizarEstadoParadaRequestDTO;
import org.arqui.microservicetravel.service.DTO.Request.FinalizarViajeRequestDTO;
import org.arqui.microservicetravel.service.DTO.Request.TravelRequestDTO;
import org.arqui.microservicetravel.service.DTO.Response.AccountInfoResponseDTO;
import org.arqui.microservicetravel.service.DTO.Response.RateInfoResponseDTO;
import org.arqui.microservicetravel.service.DTO.Response.TravelResponseDTO;
import org.arqui.microservicetravel.service.DTO.Response.ViajeConCostoResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.arqui.microservicetravel.entity.Pause;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;
    private final RateClient rateFeignClient;
    private final AccountClient accountFeignClient;
    private final ElectricScooterClient electricScooterClient;

    @Transactional
    public void save(TravelRequestDTO travel){
        travelRepository.save(TravelMapper.toEntity(travel));
    }

    @Transactional
    public void delete(Long id){
        travelRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TravelResponseDTO findById(Long id){
        Travel travel = travelRepository.findById(id).orElseThrow(()->new RuntimeException("No existe viaje con ese ID"));
        return TravelMapper.toResponse(travel);
    }

    @Transactional(readOnly = true)
    public List<TravelResponseDTO> findAll(){
        List<Travel> travels = travelRepository.findAll();
        return travels.stream().map(TravelMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public TravelResponseDTO update(Long id, TravelRequestDTO travelRequestDTO){
        Travel travel = travelRepository.findById(id).orElseThrow(()->new RuntimeException("No existe viaje con ese ID"));
        travel.setFecha_hora_inicio(travelRequestDTO.getFecha_hora_inicio());
        travel.setFecha_hora_fin(travelRequestDTO.getFecha_hora_fin());
        travel.setKmRecorridos(travelRequestDTO.getKmRecorridos());
        travel.setPausado(travelRequestDTO.getPausado());
        travel.setTarifa(travelRequestDTO.getTarifa());
        travel.setParada_inicio(travelRequestDTO.getParada_inicio());
        travel.setParada_fin(travelRequestDTO.getParada_fin());
        travel.setMonopatin(travelRequestDTO.getMonopatin());
        travel.setUsuario(travelRequestDTO.getUsuario());

        return TravelMapper.toResponse(travelRepository.save(travel));
    }

    @Transactional(readOnly = true)
    public List<String> buscarPorCantidadDeViajesYAño(Integer cantidadViajes, Integer anio) {
        return travelRepository.buscarPorCantidadDeViajesYAño(cantidadViajes, anio);
    }

    @Transactional(readOnly = true)
    public List<Long> buscarUsuariosConMasViajes(LocalDate inicio, LocalDate fin) {
        return travelRepository.buscarUsuariosConMasViajes(inicio, fin);
    }

    @Transactional(readOnly = true)
    public List<Long> buscarTarifas(LocalDateTime inicio, LocalDateTime fin) {
        return travelRepository.buscarTarifas(inicio, fin);
    }

    @Transactional(readOnly = true)
    public List<TravelResponseDTO> obtenerViajesPorUsuario(Long userId, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Travel> viajes = travelRepository.findByUsuarioAndFechaBetween(userId, fechaInicio, fechaFin);

        return viajes.stream().map(TravelMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public TravelResponseDTO finalizarViaje(Long travelId, FinalizarViajeRequestDTO request) {
        // Buscar el viaje
        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + travelId));

        // Validar que el viaje esté en curso
        if (travel.getFecha_hora_fin() != null) {
            throw new RuntimeException("El viaje ya fue finalizado");
        }

        // Actualizar datos del viaje
        travel.setParada_fin(request.getParadaFinId());
        travel.setFecha_hora_fin(LocalDateTime.now());

        // Calcular duración en minutos
        long minutos = ChronoUnit.MINUTES.between(travel.getFecha_hora_inicio(), travel.getFecha_hora_fin());

        // Guardar viaje
        Travel travelFinalizado = travelRepository.save(travel);

        // ⭐ ACTUALIZAR ESTADO DEL MONOPATÍN VIA FEIGN
        try {
            ActualizarEstadoParadaRequestDTO scooterRequest = new ActualizarEstadoParadaRequestDTO(
                    "DISPONIBLE",
                    request.getParadaFinId(),
                    request.getLatitud(),
                    request.getLongitud()
            );

            electricScooterClient.actualizarEstadoEnParada(
                    travel.getMonopatin(),
                    scooterRequest
            );

            System.out.println("✅ Monopatín " + travel.getMonopatin() +
                    " actualizado a DISPONIBLE en parada " + request.getParadaFinId());

        } catch (Exception e) {
            System.err.println("⚠ Error al actualizar estado del monopatín: " + e.getMessage());
            // Decidir si hacer rollback o continuar
            // throw new RuntimeException("Error al actualizar monopatín", e);
        }

        return TravelMapper.toResponse(travelFinalizado);
    }



    @Transactional(readOnly = true)
    public List<ViajeConCostoResponseDTO> calcularCostosDeViajes(Integer anio, Integer mesInicio, Integer mesFin) {
        List<Travel> viajes = travelRepository.buscarViajesParaFacturacion(anio, mesInicio, mesFin);
        
        return viajes.stream()
                .map(this::calcularCostoViaje)
                .collect(Collectors.toList());
    }
    
    private ViajeConCostoResponseDTO calcularCostoViaje(Travel viaje) {
        // 1. Obtener tarifa vigente en la fecha del viaje
        RateInfoResponseDTO rate = rateFeignClient.getRateByDate(viaje.getFecha_hora_inicio());
        
        // 2. Obtener información de la cuenta
        AccountInfoResponseDTO account = accountFeignClient.getAccountByUserId(viaje.getUsuario());
        
        // 3. Calcular costo base
        Double costoBase = rate.getTarifa();
        Double costoExtra = 0.0;
        boolean tienePausaLarga = false;
        
        // 4. Verificar pausas > 15 minutos
        if (viaje.getPausas() != null && !viaje.getPausas().isEmpty()) {
            for (Pause pausa : viaje.getPausas()) {
                if (pausa.getHora_inicio() != null && pausa.getHora_fin() != null) {
                    long minutosEnPausa = Duration.between(
                        pausa.getHora_inicio(), 
                        pausa.getHora_fin()
                    ).toMinutes();
                    
                    if (minutosEnPausa > 15) {
                        costoExtra += rate.getTarifaExtra();
                        tienePausaLarga = true;
                    }
                }
            }
        }
        
        // 5. Costo total (sin aplicar descuentos de premium aún)
        Double costoTotal = costoBase + costoExtra;
        
        // 6. Crear DTO
        ViajeConCostoResponseDTO dto = new ViajeConCostoResponseDTO();
        dto.setId_travel(viaje.getId_travel());
        dto.setFecha_hora_inicio(viaje.getFecha_hora_inicio());
        dto.setKmRecorridos(viaje.getKmRecorridos());
        dto.setUsuario(viaje.getUsuario());
        dto.setTipoCuenta(account.getTipoCuenta());
        dto.setCostoBase(costoBase);
        dto.setCostoExtra(costoExtra);
        dto.setCostoTotal(costoTotal);
        dto.setTienePausaLarga(tienePausaLarga);
        
        return dto;
    }
}
