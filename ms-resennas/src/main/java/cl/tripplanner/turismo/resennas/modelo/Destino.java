package cl.tripplanner.turismo.resennas.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "destinos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre", "pais"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "pais", nullable = false, length = 120)
    private String pais;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "destino")
    private List<Resenna> resennas;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Destino)) return false;
        Destino that = (Destino) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
