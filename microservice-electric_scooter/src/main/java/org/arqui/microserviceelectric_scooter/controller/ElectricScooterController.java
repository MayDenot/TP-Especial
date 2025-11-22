package org.arqui.microserviceelectric_scooter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arqui.microserviceelectric_scooter.entity.ElectricScooter;
import org.arqui.microserviceelectric_scooter.service.DTO.Request.ElectricScooterRequestDTO;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ElectricScooterResponseDTO;
import org.arqui.microserviceelectric_scooter.service.DTO.Response.ReporteUsoScooterDTO;
import org.arqui.microserviceelectric_scooter.service.ElectricScooterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scooters")
@RequiredArgsConstructor
@Tag(name = "Monopatínes", description = "Gestión de monopatines eléctricos")
public class ElectricScooterController {
    private final ElectricScooterService electricScooterService;

    @Operation(
            summary = "Crear nuevo monopatín",
            description = "Registra un nuevo monopatín eléctrico en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Monopatín creado exitosamente",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ElectricScooterRequestDTO electricScooter) {
        try {
            this.electricScooterService.save(electricScooter);
            return ResponseEntity.status(HttpStatus.CREATED).body("scooter creado con exito");
        } catch (IllegalArgumentException e) {
            // Validaciones de negocio específicas
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Mal formato de monopatin");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor");
        }
    }


    @Operation(
            summary = "Buscar monopatines cercanos",
            description = "Obtiene todos los monopatines disponibles en un radio de 2km de las coordenadas especificadas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de monopatines cercanos (puede estar vacía)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ElectricScooterResponseDTO.class),
                            array = @ArraySchema(schema = @Schema(implementation = ElectricScooterResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error al procesar la solicitud"
            )
    })

    @GetMapping("/latitud/{latitud}/longitud/{longitud}")
    public ResponseEntity<?> obtenerScootersPorLatitudYLongitud(
            @Parameter(description = "Latitud de la ubicación", example = "-37.3216", required = true)
            @Valid @PathVariable Double latitud,
            @Parameter(description = "Longitud de la ubicación", example = "-59.1344", required = true)
            @Valid @PathVariable Double longitud) {
        try {
            List<ElectricScooterResponseDTO> scooters =
                    electricScooterService.obtenerCercanos(latitud, longitud);

            if (scooters.isEmpty()) {
                // Retornar 200 OK con mensaje informativo (mejor UX)
                return ResponseEntity.ok(Map.of(
                        "mensaje", "No hay monopatines disponibles cerca de tu ubicación",
                        "scooters", List.of(),
                        "radio_km", 2.0
                ));
                // O si prefieres 204 No Content:
                // return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(scooters);

        } catch (Exception e) {
            System.err.println("❌ Error en obtenerScootersPorLatitudYLongitud: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }


    @Operation(
            summary = "Actualizar monopatín",
            description = "Modifica los datos de un monopatín existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Monopatín actualizado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Monopatín no encontrado"
            )
    })

    @PutMapping("/{id}")
    public ResponseEntity<?> modifier(
            @Parameter(description = "ID del monopatín", example = "SCOOTER-001", required = true)
            @Valid  @PathVariable String id,
            @RequestBody ElectricScooterRequestDTO electricScooterDTO) {
        try {

            electricScooterService.modifier(id, electricScooterDTO);
            return ResponseEntity.status(HttpStatus.OK).body("actualizado con exito ");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se pudo actualizar");
        }

    }


    @Operation(
            summary = "Eliminar monopatín",
            description = "Elimina un monopatín del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Monopatín eliminado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Monopatín no encontrado"
            )
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del monopatín", example = "SCOOTER-001", required = true)
            @Valid @PathVariable String id) {
        try {
            this.electricScooterService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("eliminado con exito");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se pudo eliminar");
        }
    }


    @Operation(
            summary = "Obtener monopatín por ID",
            description = "Recupera la información detallada de un monopatín específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Monopatín encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ElectricScooterResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Monopatín no encontrado"
            )
    })

    @GetMapping("/{id}")
    public ResponseEntity<ElectricScooterResponseDTO> getById(
            @Parameter(description = "ID del monopatín", required = true)
            @PathVariable String id) {
        ElectricScooterResponseDTO scooter = this.electricScooterService.getById(id);
        return ResponseEntity.ok(scooter); // 200 OK
    }


    @Operation(
            summary = "Obtener monopatines por viajes y año",
            description = "Lista los monopatines que tienen al menos X cantidad de viajes en un año específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de monopatines que cumplen el criterio",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ElectricScooterResponseDTO.class))
                    )
            )
    })

    @GetMapping("/cantidad/{cantidad}/anio/{anio}")
    public ResponseEntity<List<ElectricScooterResponseDTO>> obtenerMonopatinesDeTravel(
            @Parameter(description = "Cantidad de viajes", example = "10", required = true)
            @PathVariable Integer cantidad,
            @Parameter(description = "Año de referencia", example = "2024", required = true)
            @PathVariable Integer anio) {
        List<ElectricScooterResponseDTO> monopatines = this.electricScooterService.obtenerMonopatinesByAnioYCantidad(cantidad, anio);
        return ResponseEntity.ok(monopatines);
    }

    @Operation(
            summary = "Generar reporte de uso",
            description = "Obtiene un reporte detallado del uso de todos los monopatines, con opción de incluir tiempo de pausas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reporte generado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReporteUsoScooterDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay datos para generar el reporte"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error al generar el reporte"
            )
    })

    @GetMapping("/reporte")
    public ResponseEntity<List<ReporteUsoScooterDTO>> obtenerMonopatines(
            @Parameter(description = "Incluir tiempo de pausas en el reporte", example = "false")
            @RequestParam(defaultValue = "false") boolean incluirPausas) {
        try {
            List<ReporteUsoScooterDTO> reporte = electricScooterService.generarReporteUso(incluirPausas);

            if (reporte.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @Operation(
            summary = "Actualizar estado del monopatín",
            description = "Actualiza el estado operacional y la ubicación de un monopatín en una parada"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado actualizado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ElectricScooterResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación en los datos enviados"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Monopatín no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno al actualizar el estado"
            )
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstadoEnParada(
            @Parameter(description = "ID del monopatín", required = true)
            @Valid @PathVariable String id,
            @Valid @RequestBody ElectricScooterRequestDTO dto) {
        try {
            ElectricScooterResponseDTO scooter = electricScooterService.actualizarEstadoEnParada(
                    id,
                    String.valueOf(dto.getEstado()),
                    dto.getIdParadaActual(),
                    dto.getLatitud(),
                    dto.getLongitud()
            );
            return ResponseEntity.ok(scooter);
        } catch (IllegalArgumentException e) {
            // 400 Bad Request - Errores de validación
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error de validación: " + e.getMessage());
        } catch (RuntimeException e) {
            // 404 Not Found - Scooter no encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            // 500 Internal Server Error - Otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar estado: " + e.getMessage());
        }
    }


}
