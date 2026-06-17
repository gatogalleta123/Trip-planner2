package cl.tripplanner.common.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDeletedEvent implements UsuarioEvent {

    private String email;
    private LocalDateTime fechaEliminacion;
}