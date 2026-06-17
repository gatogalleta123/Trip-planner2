package cl.tripplanner.turismo.reservas.service;

import cl.tripplanner.turismo.reservas.modelo.Reserva;
import cl.tripplanner.turismo.reservas.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva crearReserva(Reserva reserva) {
        reserva.setEstado("CONFIRMADA");
        return reservaRepository.save(reserva);
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
            reservaRepository.save(reserva);
        }
    }

    public List<Reserva> historialCliente(Integer clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }
}