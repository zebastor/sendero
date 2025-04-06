package unillanos.sendero.servicios;

import unillanos.sendero.modelo.Especimen;
import unillanos.sendero.modelo.Estacion;

import java.util.Set;

public interface EstacionService {


    Estacion agregarEstacion(Estacion estacion);

    Estacion actualizarEstacion(Estacion estacion);

    Set<Estacion> obtenerEstaciones();

    Estacion obtenerEstacion(Integer id);

    void eliminarEstacion(Integer id);
}

