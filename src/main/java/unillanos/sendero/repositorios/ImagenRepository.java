package unillanos.sendero.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import unillanos.sendero.modelo.Imagen;

public interface ImagenRepository extends JpaRepository<Imagen, Integer> {
}
