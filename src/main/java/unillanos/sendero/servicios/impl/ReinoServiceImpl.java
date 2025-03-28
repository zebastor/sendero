package unillanos.sendero.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unillanos.sendero.modelo.Reino;
import unillanos.sendero.repositorios.ReinoRepository;
import unillanos.sendero.servicios.ReinoService;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class ReinoServiceImpl implements ReinoService {

    @Autowired
    private ReinoRepository reinoRepository;

    @Override
    public Reino agregarReino(Reino reino) {
        return reinoRepository.save(reino);
    }

    @Override
    public Reino actualizarReino(Reino reino) {
        return reinoRepository.save(reino);
    }

    @Override
    public Set<Reino> obtenerReinos() {
        return new LinkedHashSet<>(reinoRepository.findAll());
    }

    @Override
    public Reino obtenerReino(Integer id) {
        return reinoRepository.findById(id).get();
    }

    @Override
    public void eliminarReino(Integer id) {
        Reino reino = new Reino();
        reino.setId(id);
        reinoRepository.delete(reino);
    }
}
