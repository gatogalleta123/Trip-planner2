package cl.tripplanner.turismo.reportes.dto;

import lombok.Data;

@Data
public class FuenteResponse {

    private Long id;

    private String nombre;

    private String tipo;

    private Boolean activo;
}