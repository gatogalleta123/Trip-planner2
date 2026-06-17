package cl.tripplanner.turismo.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios_proyeccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioProyeccion {

    @Id
    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "nombre", length = 80)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Notificacion> notificaciones = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioProyeccion usuario)) return false;
        return email != null && email.equals(usuario.email);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}