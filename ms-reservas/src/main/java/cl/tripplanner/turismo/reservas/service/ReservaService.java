package cl.tripplanner.turismo.reservas.service;

import cl.tripplanner.common.event.ReservaCreatedEvent;
import cl.tripplanner.common.event.ReservaUpdatedEvent;
import cl.tripplanner.turismo.reservas.event.ReservaEventProducer;
import cl.tripplanner.turismo.reservas.modelo.Reserva;
import cl.tripplanner.turismo.reservas.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaEventProducer reservaEventProducer;

    public ReservaService(ReservaRepository reservaRepository, ReservaEventProducer reservaEventProducer) {
        this.reservaRepository = reservaRepository;
        this.reservaEventProducer = reservaEventProducer;
    }

    public Reserva crearReserva(Reserva reserva) {
        reserva.setEstado("CONFIRMADA");
        Reserva saved = reservaRepository.save(reserva);
        reservaEventProducer.sendCreated(
                new ReservaCreatedEvent(
                        String.valueOf(saved.getId()),
                        String.valueOf(saved.getCliente().getId()),
                        saved.getEstado()
                )
        );
        return saved;
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public Reserva buscarReserva(Integer id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public void cancelarReserva(Integer id) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva != null) {
            reserva.setEstado("CANCELADA");
            Reserva updated = reservaRepository.save(reserva);
            reservaEventProducer.sendUpdated(
                    new ReservaUpdatedEvent(
                            String.valueOf(updated.getId()),
                            String.valueOf(updated.getCliente().getId()),
                            updated.getEstado()
                    )
            );
        }
    }

    public List<Reserva> historialCliente(Integer clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }
}