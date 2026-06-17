package cl.tripplanner.turismo.auth.model;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "metodos_autenticacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetodoAutenticacion {

    @Id
    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    @OneToMany(mappedBy = "metodoAutenticacion", fetch = FetchType.LAZY)
    @Builder.Default
    private List<UsuarioAuth> usuarios = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetodoAutenticacion metodo)) return false;
        return codigo != null && codigo.equals(metodo.codigo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}