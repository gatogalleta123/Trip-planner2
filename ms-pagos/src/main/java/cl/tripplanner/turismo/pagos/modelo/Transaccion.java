package cl.tripplanner.turismo.pagos.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "transacciones", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cuenta_id", "metodo_id", "monto"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

    @ManyToOne
    @JoinColumn(name = "metodo_id", nullable = false)
    private MetodoPago metodo;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaccion)) return false;
        Transaccion that = (Transaccion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}