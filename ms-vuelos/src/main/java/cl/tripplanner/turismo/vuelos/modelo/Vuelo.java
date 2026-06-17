package cl.tripplanner.turismo.vuelos.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "vuelos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"aerolinea_id", "ruta_id", "fecha_salida"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "aerolinea_id", nullable = false)
    private Aerolinea aerolinea;

    @ManyToOne
    @JoinColumn(name = "ruta_id", nullable = false)
    private Ruta ruta;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDateTime fechaSalida;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vuelo)) return false;
        Vuelo that = (Vuelo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
