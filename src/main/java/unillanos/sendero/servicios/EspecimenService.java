package unillanos.sendero.servicios;

import unillanos.sendero.modelo.Especimen;
import unillanos.sendero.modelo.EspecimenEtapa;
import unillanos.sendero.modelo.Etapa;

import java.util.Set;

public interface EspecimenService {

    Especimen agregarEspecimen(Especimen especimen);

    Especimen actualizarEspecimen(Especimen especimen);

    Set<Especimen> obtenerEspecimenes();

    Especimen obtenerEspecimen(Integer id);

    void eliminarEspecimen(Integer id);
}
