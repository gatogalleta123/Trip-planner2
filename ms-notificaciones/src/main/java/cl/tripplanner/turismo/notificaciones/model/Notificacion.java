package cl.tripplanner.turismo.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_email", referencedColumnName = "email")
    private UsuarioProyeccion usuario;

    @Column(name = "canal", length = 20)
    private String canal;

    @Column(name = "mensaje", length = 255)
    private String mensaje;

    @Column(name = "enviada")
    private Boolean enviada;

    @OneToMany(mappedBy = "notificacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<LogNotificacion> logs = new ArrayList<>();

    @Transient
    private String estadoTemporal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notificacion that)) return false;
        return codigo != null && codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}