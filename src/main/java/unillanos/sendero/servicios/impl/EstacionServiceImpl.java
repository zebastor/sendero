package unillanos.sendero.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unillanos.sendero.modelo.*;
import unillanos.sendero.repositorios.EspecimenRepository;
import unillanos.sendero.repositorios.EstacionRepository;
import unillanos.sendero.repositorios.EtapaRepository;
import unillanos.sendero.servicios.EstacionService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EstacionServiceImpl implements EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;
    @Autowired
    private EspecimenRepository especimenRepository;

    @Override
    @Transactional
    public Estacion agregarEstacion(Estacion estacion) {
        Set<Especimen> especimenes = estacion.getEspecimenes().stream()
                .map(especimen -> especimenRepository.findById(especimen.getId())
                        .orElseThrow(() -> new RuntimeException("Especimen no encontrada")))
                .collect(Collectors.toSet());

        estacion.setEspecimenes(especimenes);

        return estacionRepository.save(estacion);
    }

    @Override
    public Estacion actualizarEstacion(Estacion estacion) {
        // Primero, obtenemos la estación existente por su ID
        Estacion estacionExistente = estacionRepository.findById(estacion.getId())
                .orElseThrow(() -> new RuntimeException("Estacion no encontrada"));

        // Actualizamos los campos básicos
        estacionExistente.setNombre(estacion.getNombre());
        estacionExistente.setLatitud(estacion.getLatitud());
        estacionExistente.setLongitud(estacion.getLongitud());
        estacionExistente.setNumero(estacion.getNumero());
        estacionExistente.setElementoInteractivo(estacion.getElementoInteractivo());

        // Actualizamos la colección de especimenes solo si se proveen en la petición
        if (estacion.getEspecimenes() != null && !estacion.getEspecimenes().isEmpty()) {
            Set<Especimen> especimenes = estacion.getEspecimenes().stream()
                    .map(especimen -> especimenRepository.findById(especimen.getId())
                            .orElseThrow(() -> new RuntimeException("Especimen no encontrado")))
                    .collect(Collectors.toSet());
            estacionExistente.setEspecimenes(especimenes);
        }

        return estacionRepository.save(estacionExistente);
    }


    @Override
    public Set<Estacion> obtenerEstaciones() {
        return new LinkedHashSet<>(estacionRepository.findAll());
    }

    @Override
    public Estacion obtenerEstacion(Integer id) {
        return estacionRepository.findById(id).get();
    }

    @Override
    public Estacion obtenerEstacionPorNumero(Integer numero) {
        return estacionRepository.findByNumero(numero);
    }

    @Override
    public void eliminarEstacion(Integer id) {
        Estacion estacion = new Estacion();
        estacion.setId(id);
        estacionRepository.delete(estacion);
    }
}
