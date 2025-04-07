package unillanos.sendero.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import unillanos.sendero.modelo.Actividad;

public interface ActvidadRepository extends JpaRepository<Actividad, Integer> {
}
