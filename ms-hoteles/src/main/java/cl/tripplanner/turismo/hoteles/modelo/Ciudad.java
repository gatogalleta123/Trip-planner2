package cl.tripplanner.turismo.hoteles.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ciudades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String pais;

    @Column(nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "ciudad")
    private List<Hotel> hoteles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ciudad)) return false;
        Ciudad ciudad = (Ciudad) o;
        return Objects.equals(id, ciudad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}