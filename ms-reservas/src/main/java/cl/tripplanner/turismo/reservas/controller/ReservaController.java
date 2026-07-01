package cl.tripplanner.turismo.reservas.controller;

import cl.tripplanner.turismo.reservas.modelo.Reserva;
import cl.tripplanner.turismo.reservas.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public Reserva crear(@RequestBody Reserva reserva) {
        return reservaService.crearReserva(reserva);
    }

    @GetMapping
    public List<Reserva> listar() {
        return reservaService.listarReservas();
    }

    @GetMapping("/{id}")
    public Reserva buscar(@PathVariable Integer id) {
        return reservaService.buscarReserva(id);
    }

    @DeleteMapping("/{id}")
    public void cancelar(@PathVariable Integer id) {
        reservaService.cancelarReserva(id);
    }


    @GetMapping("/cliente/{clienteId}")
    public List<Reserva> historial(@PathVariable Integer clienteId) {
        return reservaService.historialCliente(clienteId);
    }
}