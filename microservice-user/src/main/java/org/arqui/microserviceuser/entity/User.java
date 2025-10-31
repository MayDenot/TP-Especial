package org.arqui.microserviceuser.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arqui.microserviceuser.Rol;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    @Column
    // El profe no lo puso
    private String nombreUsuario;
    @Column
    private String nombre;
    @Column
    // El profe usar nombre para ambos
    private String apellido;
    @Column
    private String email;
    @Column
    // El profe no lo puso
    private Integer numeroCelular;
    @Column
    private Rol rol;
    @Column
    private Float latitud;
    @Column
    private Float longitud;
    @Column
    private List<ElectricScooter> monopatines; // Todos los monopatines que ha usado el usuario (se obtiene desde Travel)
    @Column
    private List<Travel> viajes; // Crear model de Travel en este microservicio
    //Faltan relaciones

    //private List<Account> cuentas;
}
