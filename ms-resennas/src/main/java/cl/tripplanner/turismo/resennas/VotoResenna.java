package cl.tripplanner.turismo.resennas;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "votos_resennas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotoResenna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "resenna_codigo")
    private String resennaCodigo;

    private String usuario;

    private Boolean util;

    private LocalDateTime fecha;

}
