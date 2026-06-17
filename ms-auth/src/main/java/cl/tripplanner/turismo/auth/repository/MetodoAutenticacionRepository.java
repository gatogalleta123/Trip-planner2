package cl.tripplanner.turismo.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tripplanner.turismo.auth.model.MetodoAutenticacion;

public interface MetodoAutenticacionRepository extends JpaRepository<MetodoAutenticacion, String> {

}
