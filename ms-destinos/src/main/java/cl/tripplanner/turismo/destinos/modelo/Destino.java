package cl.tripplanner.turismo.destinos.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "destinos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre", "pais_id"})
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

    @ManyToOne
    @JoinColumn(name = "pais_id", nullable = false)
    private Pais pais;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "destino")
    private List<Atraccion> atracciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Destino)) return false;
        Destino destino = (Destino) o;
        return Objects.equals(id, destino.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
