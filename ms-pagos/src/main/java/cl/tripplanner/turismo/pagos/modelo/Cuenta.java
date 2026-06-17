package cl.tripplanner.turismo.pagos.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cuentas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "moneda"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "saldo", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo;

    @Column(name = "moneda", nullable = false, length = 3)
    private String moneda;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "cuenta")
    private List<Transaccion> transacciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuenta)) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(id, cuenta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}