package org.arqui.microservicetravel.repository;

import org.arqui.microservicetravel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

}
