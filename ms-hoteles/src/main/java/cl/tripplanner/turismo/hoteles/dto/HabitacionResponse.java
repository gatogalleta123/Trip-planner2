package cl.tripplanner.turismo.hoteles.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitacionResponse {

    private Integer id;
    private String tipo;
    private BigDecimal precio;
    private Boolean disponible;
}