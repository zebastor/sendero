package unillanos.sendero.servicios;

import unillanos.sendero.modelo.Etapa;

import java.util.Set;

public interface EtapaService {

    Etapa agregarEtapa(Etapa etapa);

    Etapa actualizarEtapa(Etapa etapa);

    Set<Etapa> obtenerEtapas();

    Etapa obtenerEtapa(Integer id);

    void eliminarEtapa(Integer id);
}
