package cl.tripplanner.turismo.resennas.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Table(name = "resennas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "destino_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resenna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioResenna usuario;

    @ManyToOne
    @JoinColumn(name = "destino_id", nullable = false)
    private Destino destino;

    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;

    @Column(name = "comentario", length = 255)
    private String comentario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resenna)) return false;
        Resenna that = (Resenna) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
