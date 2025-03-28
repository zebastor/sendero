package unillanos.sendero.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import unillanos.sendero.modelo.Especimen;

public interface EspecimenRepository extends JpaRepository<Especimen, Integer> {
}
