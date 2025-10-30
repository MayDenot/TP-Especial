package org.arqui.microservicemaintenance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arqui.microservicemaintenance.State;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Maintenance {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_mantenimiento;
  @Column
  private Date fechaEntrada;
  @Column
  private Date fechaSalida;
  @Column
  private String descripcion;
  @Column
  private State estado;
  //private List<ElectricScooter> monopatines; // preguntar si va con lista
}
