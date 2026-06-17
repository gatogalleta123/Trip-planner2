package cl.tripplanner.turismo.reportes.dto;

import lombok.Data;

@Data
public class ReporteResponse {

    private Long id;

    private String nombre;

    private Long fuenteId;

    private String fuenteNombre;

    private String formato;

    private Boolean activo;
}