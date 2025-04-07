package unillanos.sendero.servicios;

import unillanos.sendero.modelo.Actividad;
import unillanos.sendero.modelo.Especimen;

import java.util.Set;

public interface ActividadService {

    Actividad agregarActividad(Actividad actividad);

    Actividad actualizarActividad(Actividad actividad);

    Set<Actividad> obtenerActividades();

    Actividad obtenerActividad(Integer id);

    void eliminarActividad(Integer id);
}
