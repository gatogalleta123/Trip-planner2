package cl.tripplanner.turismo.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "telefono", length = 30)
    private String telefono;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "activo", nullable = false)
    private Boolean activo;



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return email != null && email.equals(usuario.email);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

