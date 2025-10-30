package org.arqui.microserviceuser.entity;
import org.arqui.microserviceuser.Rol;

@Entity
@Data
@NoArgConstructor
@AllArgsConstructor

public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_persona;
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private Integer numeroCelular;
    private Rol rol;

    private List<Travel> viajes;
    private List<Account> cuentas;
}
