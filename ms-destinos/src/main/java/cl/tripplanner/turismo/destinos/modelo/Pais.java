package cl.tripplanner.turismo.destinos.modelo;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "paises", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nombre"),
        @UniqueConstraint(columnNames = "codigo_iso")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "codigo_iso", nullable = false, length = 2)
    private String codigoIso;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "pais")
    private List<Destino> destinos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pais)) return false;
        Pais pais = (Pais) o;
        return Objects.equals(id, pais.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
