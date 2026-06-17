package cl.tripplanner.turismo.hoteles.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "hoteles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;

    @Column(nullable = false)
    private Integer estrellas;

    @Column(nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "hotel")
    private List<Habitacion> habitaciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(id, hotel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}