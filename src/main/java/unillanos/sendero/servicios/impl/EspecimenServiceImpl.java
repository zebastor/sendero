package unillanos.sendero.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unillanos.sendero.modelo.Especimen;
import unillanos.sendero.modelo.Etapa;
import unillanos.sendero.modelo.Reino;
import unillanos.sendero.repositorios.EspecimenRepository;
import unillanos.sendero.repositorios.EtapaRepository;
import unillanos.sendero.repositorios.ReinoRepository;
import unillanos.sendero.servicios.EspecimenService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EspecimenServiceImpl implements EspecimenService {
    @Autowired
    private EspecimenRepository especimenRepository;
    @Autowired
    private ReinoRepository reinoRepository;
    @Autowired
    private EtapaRepository etapaRepository;

    @Override
    @Transactional
    public Especimen agregarEspecimen(Especimen especimen) {
        // Cargar etapas existentes
        Set<Etapa> etapas = especimen.getEtapas().stream()
                .map(etapa -> etapaRepository.findById(etapa.getId())
                        .orElseThrow(() -> new RuntimeException("Etapa no encontrada")))
                .collect(Collectors.toSet());

        especimen.setEtapas(etapas);
        return especimenRepository.save(especimen);
    }
    @Override
    public Especimen actualizarEspecimen(Especimen especimen) {
        return especimenRepository.save(especimen);
    }

    @Override
    public Set<Especimen> obtenerEspecimenes() {
        return new LinkedHashSet<>(especimenRepository.findAll());
    }

    @Override
    public Especimen obtenerEspecimen(Integer id) {
        return especimenRepository.findById(id).get();
    }

    @Override
    public void eliminarEspecimen(Integer id) {
        Especimen especimen = new Especimen();
        especimen.setId(id);
        especimenRepository.delete(especimen);
    }
}
