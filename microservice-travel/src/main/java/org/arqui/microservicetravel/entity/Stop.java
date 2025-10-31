package org.arqui.microservicetravel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_stop;
    @Column
    private Boolean ocupada;
    @Column
    private List<ElectricScooter> monopatines;
    @Column
    private Double latitud;
    @Column
    private Double longitud;
}
