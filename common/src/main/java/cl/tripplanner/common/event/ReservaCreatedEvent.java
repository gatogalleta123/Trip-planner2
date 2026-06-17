package cl.tripplanner.common.event;

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
public class ReservaCreatedEvent implements ReservaEvent {

    private String id;
    private String clienteId;
    private String estado;

}
