package org.arqui.microserviceuser.entity;

import org.arqui.microserviceuser.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;


@Entity
@Data
@NoArgConstructor
@AllArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    @Column
    private String nombreUsuario;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String email;
    @Column
    private Integer numeroCelular;
    @Column
    private Rol rol;

    //Faltan relaciones
    //private List<Travel> viajes;
    private List<Account> cuentas;
}
