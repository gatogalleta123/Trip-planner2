package cl.tripplanner.turismo.notificaciones.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.tripplanner.common.exception.EntityNotFoundException;
import cl.tripplanner.turismo.notificaciones.dto.UsuarioProyeccionResponse;
import cl.tripplanner.turismo.notificaciones.mapper.UsuarioProyeccionMapper;
import cl.tripplanner.turismo.notificaciones.model.UsuarioProyeccion;
import cl.tripplanner.turismo.notificaciones.repository.UsuarioProyeccionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioProyeccionService {
    private final UsuarioProyeccionRepository usuarioProyeccionRepository;
    private final UsuarioProyeccionMapper usuarioProyeccionMapper;

    @Transactional
    public List<UsuarioProyeccionResponse> findAll() {
        return usuarioProyeccionMapper.toResponseList(usuarioProyeccionRepository.findAll());
    }

    @Transactional
    public void save(String email, String nombre, Boolean activo) {
        UsuarioProyeccion usuarioProyeccion = new UsuarioProyeccion();
        usuarioProyeccion.setEmail(email);
        usuarioProyeccion.setNombre(nombre);
        usuarioProyeccion.setActivo(activo);
        usuarioProyeccionRepository.save(usuarioProyeccion);
    }

    @Transactional
    public void deleteByEmail(String email) {
        UsuarioProyeccion usuarioProyeccion = usuarioProyeccionRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("UsuarioProyeccion", "email", email));
        usuarioProyeccionRepository.delete(usuarioProyeccion);
    }

    @Transactional
    public void update(String email, String nombre, Boolean activo) {
        UsuarioProyeccion usuarioProyeccion = usuarioProyeccionRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("UsuarioProyeccion", "email", email));
        usuarioProyeccion.setNombre(nombre);
        usuarioProyeccion.setActivo(activo);
        usuarioProyeccionRepository.save(usuarioProyeccion);
    }
}
