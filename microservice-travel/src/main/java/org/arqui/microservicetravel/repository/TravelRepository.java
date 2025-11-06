package org.arqui.microservicetravel.repository;

import org.arqui.microservicetravel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {
    @Query("""
        SELECT t.monopatin
        FROM Travel t
        WHERE FUNCTION('YEAR', t.fecha_hora_inicio) = :anio
        GROUP BY t.monopatin
        HAVING COUNT(t.id_travel) >= :cantidadViajes
    """)
    List<String> buscarPorCantidadDeViajesYAño(@Param("cantidadViajes") Integer cantidadViajes, 
                                                @Param("anio") Integer anio);

    @Query("""
        SELECT t.usuario
        FROM Travel t
        WHERE t.fecha_hora_inicio BETWEEN :inicio AND :fin
        GROUP BY t.usuario
        ORDER BY COUNT(t.id_travel) DESC
""")
    List<Long> buscarUsuariosConMasViajes(@Param("inicio")LocalDate inicio, @Param("fin") LocalDate fin);

    @Query("""
        SELECT t.tarifa
        FROM Travel t
        WHERE t.fecha_hora_inicio BETWEEN :inicio AND :fin
        ORDER BY t.fecha_hora_inicio
""")
    List<Long> buscarTarifas(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
