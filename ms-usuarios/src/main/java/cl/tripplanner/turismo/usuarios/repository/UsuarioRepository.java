package cl.tripplanner.turismo.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.tripplanner.turismo.usuarios.model.*;;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByRolId(Integer rolId);

    boolean existsByOrganizacionId(Integer organizacionId);
}
