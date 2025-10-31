package org.arqui.microserviceuser.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_account;
    @Column
    private LocalDateTime fechaDeAlta;
    @Column
    private Double montoDisponible;
    @Column
    private boolean estado;
    //Falta relación
    private List<User> usuarios;

}
