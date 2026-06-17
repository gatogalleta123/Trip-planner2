package cl.tripplanner.turismo.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "roles",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_roles_nombre", columnNames = "nombre")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "rol")
    @Builder.Default
    private List<Usuario> usuarios = new ArrayList<>();
}