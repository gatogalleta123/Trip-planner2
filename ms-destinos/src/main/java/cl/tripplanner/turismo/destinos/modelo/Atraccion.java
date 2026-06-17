package cl.tripplanner.turismo.destinos.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "atracciones", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre", "destino_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atraccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "destino_id", nullable = false)
    private Destino destino;

    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "disponible", nullable = false)
    private Boolean disponible;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Atraccion)) return false;
        Atraccion that = (Atraccion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
