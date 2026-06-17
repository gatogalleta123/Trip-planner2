package cl.tripplanner.turismo.hoteles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitacionRequest {

    @NotNull(message = "El hotelId es obligatorio")
    private Integer hotelId;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotNull(message = "El precio es obligatorio")
    private BigDecimal precio;

    @NotNull(message = "La disponibilidad es obligatoria")
    private Boolean disponible;
}