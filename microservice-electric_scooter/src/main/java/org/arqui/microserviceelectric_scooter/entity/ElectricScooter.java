package org.arqui.microserviceelectric_scooter.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.arqui.microserviceelectric_scooter.EstadoScooter;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "electric_scooters") // 👈 nombre de la colección en Mongo
public class ElectricScooter {

    @Id
    private String id; // Mongo usa IDs tipo String (ObjectId)

    private Double longitud;
    private Double latitud;
    private Boolean habilitado = true;
    private Integer bateria;
    private Long tiempoDeUso = 0L;
    private Double kilometrosRecorridos = 0.0;
    private Boolean enParada = true;
    private String codigoQR;
    private EstadoScooter estado = EstadoScooter.DISPONIBLE;
    private LocalDateTime fechaAlta;
    private LocalDateTime ultimaActualizacion;
    private Long idParadaActual;

    public void onCreate() {
        fechaAlta = LocalDateTime.now();
        ultimaActualizacion = LocalDateTime.now();
        if (codigoQR == null) {
            codigoQR = "QR-" + System.currentTimeMillis();
        }
    }

    public void onUpdate() {
        ultimaActualizacion = LocalDateTime.now();
    }
}