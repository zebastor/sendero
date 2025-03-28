package unillanos.sendero.servicios;

import unillanos.sendero.modelo.Reino;

import java.util.Set;


public interface ReinoService {

    Reino agregarReino(Reino reino);

    Reino actualizarReino(Reino reino);

    Set<Reino> obtenerReinos();

    Reino obtenerReino(Integer id);

    void eliminarReino(Integer id);
}
