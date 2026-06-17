package cl.tripplanner.turismo.auth.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios_auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioAuth {

    @Id
    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metodo_codigo", referencedColumnName = "codigo")
    private MetodoAutenticacion metodoAutenticacion;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<AuditoriaLogin> auditorias = new ArrayList<>();

    @Transient
    private String tokenTemporal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioAuth usuario)) return false;
        return email != null && email.equals(usuario.email);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}