package unillanos.sendero.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unillanos.sendero.modelo.Etapa;
import unillanos.sendero.modelo.Reino;
import unillanos.sendero.repositorios.EtapaRepository;
import unillanos.sendero.servicios.EtapaService;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class EtapaServiceImpl implements EtapaService {

    @Autowired
    private EtapaRepository etapaRepository;

    @Override
    public Etapa agregarEtapa(Etapa etapa) {
        return etapaRepository.save(etapa);
    }

    @Override
    public Etapa actualizarEtapa(Etapa etapa) {
        return etapaRepository.save(etapa);
    }

    @Override
    public Set<Etapa> obtenerEtapas() {
        return new LinkedHashSet<>(etapaRepository.findAll());
    }

    @Override
    public Etapa obtenerEtapa(Integer id) {
        return etapaRepository.findById(id).get();
    }

    @Override
    public void eliminarEtapa(Integer id) {
        Etapa etapa = new Etapa();
        etapa.setId(id);
        etapaRepository.delete(etapa);
    }
}
