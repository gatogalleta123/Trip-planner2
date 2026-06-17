package cl.tripplanner.turismo.reportes.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;

@Entity
@Table(name = "fuentes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fuente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "fuente")
    @Builder.Default
    private List<Reporte> reportes = new ArrayList<>();
}