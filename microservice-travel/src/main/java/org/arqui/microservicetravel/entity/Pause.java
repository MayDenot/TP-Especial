package org.arqui.microservicetravel.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Pause {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pause;
    @Column
    private LocalDateTime hora_inicio;
    @Column
    private LocalDateTime hora_fin;
}
