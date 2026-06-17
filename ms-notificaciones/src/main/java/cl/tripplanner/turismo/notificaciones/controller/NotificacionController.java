package cl.tripplanner.turismo.notificaciones.controller;

import cl.tripplanner.turismo.notificaciones.dto.LogNotificacionResponse;
import cl.tripplanner.turismo.notificaciones.dto.LogNotificacionResponse;
import cl.tripplanner.turismo.notificaciones.dto.NotificacionRequest;
import cl.tripplanner.turismo.notificaciones.dto.NotificacionResponse;
import cl.tripplanner.turismo.notificaciones.model.LogNotificacion;
import cl.tripplanner.turismo.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {
    private final NotificacionService notificacionService;

    @GetMapping
    public List<NotificacionResponse> findAll() {
        return notificacionService.findAll();
    }

    @GetMapping("/{codigo}")
    public NotificacionResponse findByCodigo(
            @PathVariable String codigo) {
        return notificacionService.findByCodigo(codigo);
    }

    @GetMapping("/canal/{canal}")
    public List<NotificacionResponse> findByCanal(
            @PathVariable String canal) {
        return notificacionService.findByCanal(canal);
    }
    
    @GetMapping("/enviada/{enviada}")
    public List<NotificacionResponse> findByEnviada(
            @PathVariable Boolean enviada) {
        return notificacionService.findByEnviada(enviada);
    }

    @PostMapping
    public NotificacionResponse create(
            @Valid @RequestBody NotificacionRequest request) {
        return notificacionService.create(request);
    }
   
    @PutMapping("/{codigo}")
    public NotificacionResponse update(
            @PathVariable String codigo,
            @Valid @RequestBody NotificacionRequest request) {
        return notificacionService.update(codigo, request);
    }

    @DeleteMapping("/{codigo}")
    public void deleteByCodigo(
            @PathVariable String codigo) {
        notificacionService.deleteByCodigo(codigo);
    }

    @PutMapping("/enviar/{codigo}")
    public void marcarComoEnviada(
            @PathVariable String codigo) {
        notificacionService.marcarComoEnviada(codigo);
    }

    @GetMapping("/logs/{codigo}")
    public List<LogNotificacionResponse> findLogsByNotificacion(
            @PathVariable String codigo) {
        return notificacionService
                .findLogsByNotificacion(codigo);
    }

}