package cl.tripplanner.turismo.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tripplanner.turismo.usuarios.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}