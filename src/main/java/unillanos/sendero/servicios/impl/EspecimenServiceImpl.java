package unillanos.sendero.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unillanos.sendero.modelo.Especimen;
import unillanos.sendero.modelo.Etapa;
import unillanos.sendero.modelo.Imagen;
import unillanos.sendero.modelo.Reino;
import unillanos.sendero.repositorios.EspecimenRepository;
import unillanos.sendero.repositorios.EtapaRepository;
import unillanos.sendero.repositorios.ImagenRepository;
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
    @Autowired
    private ImagenRepository imagenRepository;

    @Override
    @Transactional
    public Especimen agregarEspecimen(Especimen especimen) {
        // Manejar etapas existentes
        Set<Etapa> etapas = especimen.getEtapas().stream()
                .map(etapa -> etapaRepository.findById(etapa.getId())
                        .orElseThrow(() -> new RuntimeException("Etapa no encontrada")))
                .collect(Collectors.toSet());
        especimen.setEtapas(etapas);

        // Manejar reino existente
        if (especimen.getReino() != null && especimen.getReino().getId() != 0) {
            Reino reino = reinoRepository.findById(especimen.getReino().getId())
                    .orElseThrow(() -> new RuntimeException("Reino no encontrado"));
            especimen.setReino(reino);
        }

        // Para cada imagen, asignar el espécimen
        especimen.getImagenes().forEach(imagen -> imagen.setEspecimen(especimen));

        // Guardar el espécimen, lo que gracias al cascade persistirá las imágenes
        return especimenRepository.save(especimen);
    }


    @Override
    public Especimen actualizarEspecimen(Especimen especimen) {
        // Primero, obtenemos el espécimen existente por su ID
        Especimen especimenExistente = especimenRepository.findById(especimen.getId())
                .orElseThrow(() -> new RuntimeException("Especimen no encontrado"));

        // Actualizamos los campos básicos
        especimenExistente.setNombre(especimen.getNombre());

        // Actualizamos la relación con Reino (si se envía uno válido)
        if (especimen.getReino() != null && especimen.getReino().getId() != 0) {
            Reino reino = reinoRepository.findById(especimen.getReino().getId())
                    .orElseThrow(() -> new RuntimeException("Reino no encontrado"));
            especimenExistente.setReino(reino);
        } else {
            especimenExistente.setReino(null);
        }

        // Actualizamos la relación con Etapas: buscamos cada etapa por su ID y las asignamos
        Set<Etapa> etapas = especimen.getEtapas().stream()
                .map(etapa -> etapaRepository.findById(etapa.getId())
                        .orElseThrow(() -> new RuntimeException("Etapa no encontrada")))
                .collect(Collectors.toSet());
        especimenExistente.setEtapas(etapas);

        // Actualizamos las imágenes sin duplicar aquellas que ya existan (según "direccion")
        Set<Imagen> imagenesExistentes = especimenExistente.getImagenes();
        for (Imagen imagenNueva : especimen.getImagenes()) {
            boolean existe = imagenesExistentes.stream()
                    .anyMatch(imagenExistente -> imagenExistente.getDireccion().equals(imagenNueva.getDireccion()));
            if (!existe) {
                imagenNueva.setEspecimen(especimenExistente);
                imagenesExistentes.add(imagenNueva);
            }
        }
        // Si deseas remover imágenes que ya no se incluyan en la actualización, podrías aplicar una lógica adicional

        return especimenRepository.save(especimenExistente);
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
