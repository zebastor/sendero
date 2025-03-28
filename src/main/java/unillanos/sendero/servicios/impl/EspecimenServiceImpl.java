package unillanos.sendero.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unillanos.sendero.modelo.Especimen;
import unillanos.sendero.modelo.Etapa;
import unillanos.sendero.repositorios.EspecimenRepository;
import unillanos.sendero.servicios.EspecimenService;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class EspecimenServiceImpl implements EspecimenService {
    @Autowired
    private EspecimenRepository especimenRepository;

    @Override
    public Especimen agregarEspecimen(Especimen especimen) {
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
