package org.example.microservicestop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_stop;
    @Column
    private String direccion;
    @Column
    private String nombre;
    @Column
    private Float latitud;
    @Column
    private Float longitud;
}
