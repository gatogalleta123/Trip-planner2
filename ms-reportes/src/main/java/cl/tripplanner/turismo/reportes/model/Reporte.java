package cl.tripplanner.turismo.reportes.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "reportes",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "nombre",
                                "fuente_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fuente_id",
            nullable = false
    )
    private Fuente fuente;

    @Column(nullable = false, length = 20)
    private String formato;

    @Column(nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "reporte")
    @Builder.Default
    private List<HistorialReporte> ejecuciones =
            new ArrayList<>();
}