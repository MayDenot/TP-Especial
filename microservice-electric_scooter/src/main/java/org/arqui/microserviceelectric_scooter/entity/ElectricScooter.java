package org.arqui.microserviceelectric_scooter.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arqui.microserviceelectric_scooter.EstadoScooter;

import java.time.LocalDateTime;

@Entity
@Table(name = "electric_scooters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectricScooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double longitud;  // Mejor usar Double para coordenadas

    @Column(nullable = false)
    private Double latitud;   // Mejor usar Double para coordenadas

    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado = true;

    @Column(nullable = false)
    private Integer bateria;  // 0-100 porcentaje

    @Column(name = "tiempo_uso_minutos")
    private Long tiempoDeUso = 0L;  // en minutos

    @Column(name = "kilometros_recorridos")
    private Double kilometrosRecorridos = 0.0;  // Mejor Double para decimales

    @Column(name = "en_parada", nullable = false)
    private Boolean enParada = true;

    // Para el QR puedes usar el ID o generar un código único
    @Column(name = "codigo_qr", unique = true)
    private String codigoQR;

    // Campos adicionales útiles
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoScooter estado = EstadoScooter.DISPONIBLE;

    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    @Column(name = "id_parada_actual")
    private Long idParadaActual;  // FK a la parada donde está

    @PrePersist
    protected void onCreate() {
        fechaAlta = LocalDateTime.now();
        ultimaActualizacion = LocalDateTime.now();
        if (codigoQR == null) {
            codigoQR = "QR-" + System.currentTimeMillis(); // Generación simple
        }
    }

    @PreUpdate
    protected void onUpdate() {
        ultimaActualizacion = LocalDateTime.now();
    }
}