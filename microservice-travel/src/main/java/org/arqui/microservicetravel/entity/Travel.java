package org.arqui.microservicetravel.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Pause> pausas;

    @Column
    private Long tarifa;
    @Column
    private Long parada_inicio;
    @Column
    private Long parada_fin;
    @Column
    private String monopatin;
    @Column
    private Long usuario;
}
