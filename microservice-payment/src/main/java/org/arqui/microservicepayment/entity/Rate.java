package org.arqui.microservicepayment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rate {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  private Double tarifa;
  @Column
  private Double tarifaExtra;
  @Column(unique = true)
  private LocalDateTime fechaActualizacion;
  @Column
  private Boolean vigente;
}
