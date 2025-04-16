package unillanos.sendero.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import unillanos.sendero.modelo.Estacion;

public interface EstacionRepository extends JpaRepository<Estacion, Integer> {

    Estacion findByNumero(Integer numero);
}
