package cl.tripplanner.turismo.destinos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AtraccionRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El destino es obligatorio")
    private Integer destinoId;

    @NotNull(message = "El precio es obligatorio")
    private BigDecimal precio;

    @NotNull(message = "La disponibilidad es obligatoria")
    private Boolean disponible;
}