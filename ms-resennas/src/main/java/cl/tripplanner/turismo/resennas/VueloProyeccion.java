package cl.tripplanner.turismo.resennas;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vuelos_proyeccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VueloProyeccion {

    @Id
    private String codigo;

    private String origen;

    private String destino;

}