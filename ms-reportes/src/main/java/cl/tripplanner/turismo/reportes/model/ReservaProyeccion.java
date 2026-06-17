package cl.tripplanner.turismo.reportes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservas_proyeccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaProyeccion {

    @Id
    @Column(name = "id", nullable = false, length = 20)
    private String id;

    @Column(name = "cliente_id", nullable = false)
    private String clienteId;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservaProyeccion that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}