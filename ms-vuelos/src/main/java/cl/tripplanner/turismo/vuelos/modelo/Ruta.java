package cl.tripplanner.turismo.vuelos.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rutas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"origen", "destino"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "origen", nullable = false, length = 120)
    private String origen;

    @Column(name = "destino", nullable = false, length = 120)
    private String destino;

    @Column(name = "distancia_km")
    private Integer distanciaKm;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "ruta")
    private List<Vuelo> vuelos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ruta)) return false;
        Ruta ruta = (Ruta) o;
        return Objects.equals(id, ruta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
