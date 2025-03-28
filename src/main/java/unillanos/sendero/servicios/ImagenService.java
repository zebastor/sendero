package unillanos.sendero.servicios;

import unillanos.sendero.modelo.Etapa;
import unillanos.sendero.modelo.Imagen;

import java.util.Set;

public interface ImagenService {

    Imagen agregarImagen(Imagen imagen);

    Imagen actualizarImagen(Imagen imagen);

    Set<Imagen> obtenerImagenes();

    Imagen obtenerImagen(Integer id);

    void eliminarImagen(Integer id);
}
