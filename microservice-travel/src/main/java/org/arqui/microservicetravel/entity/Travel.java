package org.arqui.microservicetravel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_travel;
    @Column
    private Date fechaInicio;
    @Column
    private Date fechaFin;
    @Column
    private LocalTime horaInicio;
    @Column
    private LocalTime horaFin;
    @Column
    private Integer kmRecorridos;
    @Column
    private Boolean pausado;
    @Column
    private User usuario;
    @Column
    private Stop parada;
}
