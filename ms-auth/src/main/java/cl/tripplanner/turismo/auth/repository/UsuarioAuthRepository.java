package cl.tripplanner.turismo.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tripplanner.turismo.auth.model.UsuarioAuth;

public interface UsuarioAuthRepository extends JpaRepository<UsuarioAuth, String> {
    Optional<UsuarioAuth> findByEmail(String email);

    boolean existsByEmail(String email);
}
