package cl.tripplanner.turismo.vuelos.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "aerolineas", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nombre"),
        @UniqueConstraint(columnNames = "codigo_iata")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aerolinea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "codigo_iata", nullable = false, length = 2)
    private String codigoIata;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "aerolinea")
    private List<Vuelo> vuelos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aerolinea)) return false;
        Aerolinea that = (Aerolinea) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
