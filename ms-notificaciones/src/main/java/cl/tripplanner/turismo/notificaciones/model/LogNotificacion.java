package cl.tripplanner.turismo.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs_notificaciones")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notificacion_codigo", referencedColumnName = "codigo")
    private Notificacion notificacion;

    @CreatedDate
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "estado", length = 30)
    private String estado;

    @Column(name = "detalle", columnDefinition = "TEXT")
    private String detalle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogNotificacion that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}