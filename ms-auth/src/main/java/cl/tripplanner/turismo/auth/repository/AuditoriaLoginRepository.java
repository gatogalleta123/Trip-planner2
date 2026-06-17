package cl.tripplanner.turismo.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tripplanner.turismo.auth.model.AuditoriaLogin;

public interface AuditoriaLoginRepository extends JpaRepository<AuditoriaLogin, Long> {

    List<AuditoriaLogin> findByUsuarioEmail(String email);
}
