package org.arqui.microserviceuser.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

@Entity
@Data
@NoArgConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_account;
    @Column
    private LocaleDate fechaDeAlta;
    @Column
    private Double montoDisponible;
    @Column
    private boolean estado;

    //Falta relación
    private List<User> usuarios;

}
