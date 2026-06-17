package cl.tripplanner.turismo.hoteles.repository;

import cl.tripplanner.turismo.hoteles.modelo.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository
        extends JpaRepository<Hotel, Integer> {
}