package org.arqui.microservicetravel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_travel;
    @Column
    private LocalDateTime fecha_hora_inicio;
    @Column
    private LocalDateTime fecha_hora_fin;
    @Column
    private Integer kmRecorridos;
    @Column
    private Boolean pausado;
    @Column
    private List<Pause> pausas;
    @Column
    private Tarifa tarifa;
    @Column
    private Stop parada_inicio;
    @Column
    private Stop parada_fin;
}
