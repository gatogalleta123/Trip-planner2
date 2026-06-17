package cl.tripplanner.turismo.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tripplanner.turismo.usuarios.model.Organizacion;

public interface OrganizacionRepository extends JpaRepository<Organizacion, Integer> {

    Optional<Organizacion> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}