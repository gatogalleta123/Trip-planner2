package cl.tripplanner.turismo.reservas.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reservas", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "cliente_id", "servicio_id", "fecha_reserva" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Reserva))
            return false;
        Reserva that = (Reserva) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}