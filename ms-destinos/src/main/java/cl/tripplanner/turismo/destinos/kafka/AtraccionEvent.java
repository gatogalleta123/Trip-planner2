package cl.tripplanner.turismo.destinos.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtraccionEvent {

    private Integer id;
    private String nombre;
    private BigDecimal precio;
    private Boolean disponible;
    private String destinoNombre;
    private String accion; // CREADO / ELIMINADO
}