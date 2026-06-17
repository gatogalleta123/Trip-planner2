package cl.tripplanner.turismo.auth.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_login")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditoriaLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_email", referencedColumnName = "email")
    private UsuarioAuth usuario;

    @CreatedDate
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "ip_origen", length = 45)
    private String ipOrigen;

    @Column(name = "exitoso")
    private Boolean exitoso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditoriaLogin auditoria)) return false;
        return id != null && id.equals(auditoria.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}