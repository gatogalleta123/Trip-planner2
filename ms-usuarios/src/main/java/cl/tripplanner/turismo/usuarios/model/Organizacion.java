package cl.tripplanner.turismo.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "organizaciones",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_organizacion_nombre", columnNames = "nombre")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "dominio_email", length = 120)
    private String dominioEmail;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "organizacion")
    @Builder.Default
    private List<Usuario> usuarios = new ArrayList<>();
}