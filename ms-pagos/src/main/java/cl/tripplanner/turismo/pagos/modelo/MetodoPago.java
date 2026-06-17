package cl.tripplanner.turismo.pagos.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "metodos_pago", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tipo", "proveedor"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "proveedor", nullable = false, length = 100)
    private String proveedor;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "metodo")
    private List<Transaccion> transacciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetodoPago)) return false;
        MetodoPago that = (MetodoPago) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
