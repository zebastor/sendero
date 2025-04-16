package unillanos.sendero.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unillanos.sendero.modelo.*;
import unillanos.sendero.repositorios.*;
import unillanos.sendero.servicios.EspecimenService;

import java.util.HashSet;
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

    @Autowired
    private Imagen3dRepository imagen3dRepository;

    @Override
    @Transactional
    public Especimen agregarEspecimen(Especimen especimen) {
        // Manejar etapas existentes
        Set<Etapa> etapas = especimen.getEtapas().stream()
                .map(etapa -> etapaRepository.findById(etapa.getId())
                        .orElseThrow(() -> new RuntimeException("Etapa no encontrada")))
                .collect(Collectors.toSet());
        especimen.setEtapas(etapas);

        // Manejar reino existente (si se especifica)
        if (especimen.getReino() != null && especimen.getReino().getId() != 0) {
            Reino reino = reinoRepository.findById(especimen.getReino().getId())
                    .orElseThrow(() -> new RuntimeException("Reino no encontrado"));
            especimen.setReino(reino);
        } else {
            especimen.setReino(null);
        }

        // Manejar imágenes: se filtran duplicados según la dirección
        Set<Imagen> imagenesNuevas = new HashSet<>();
        if (especimen.getImagenes() != null) {
            for (Imagen imagen : especimen.getImagenes()) {
                boolean existe = imagenesNuevas.stream()
                        .anyMatch(img -> img.getDireccion().equals(imagen.getDireccion()));
                if (!existe) {
                    imagen.setEspecimen(especimen); // Relación bidireccional
                    imagenesNuevas.add(imagen);
                }
            }
        }
        especimen.setImagenes(imagenesNuevas);

        // Manejar imágenes 3D: también se filtran duplicados según la dirección
        Set<Imagen3d> imagenes3dNuevas = new HashSet<>();
        if (especimen.getImagenes3d() != null) {
            for (Imagen3d imagen3d : especimen.getImagenes3d()) {
                boolean existe = imagenes3dNuevas.stream()
                        .anyMatch(img -> img.getDireccion().equals(imagen3d.getDireccion()));
                if (!existe) {
                    imagen3d.setEspecimen(especimen); // Relación bidireccional
                    imagenes3dNuevas.add(imagen3d);
                }
            }
        }
        especimen.setImagenes3d(imagenes3dNuevas);

        // Guardar el espécimen (con cascade, las imágenes se persistirán)
        return especimenRepository.save(especimen);
    }



    @Override
    public Especimen actualizarEspecimen(Especimen especimen) {
        Especimen especimenExistente = especimenRepository.findById(especimen.getId())
                .orElseThrow(() -> new RuntimeException("Especimen no encontrado"));

        especimenExistente.setNombre(especimen.getNombre());
        especimenExistente.setDescripcion(especimen.getDescripcion());

        if (especimen.getReino() != null && especimen.getReino().getId() != 0) {
            Reino reino = reinoRepository.findById(especimen.getReino().getId())
                    .orElseThrow(() -> new RuntimeException("Reino no encontrado"));
            especimenExistente.setReino(reino);
        } else {
            especimenExistente.setReino(null);
        }

        Set<Etapa> etapas = especimen.getEtapas().stream()
                .map(etapa -> etapaRepository.findById(etapa.getId())
                        .orElseThrow(() -> new RuntimeException("Etapa no encontrada")))
                .collect(Collectors.toSet());
        especimenExistente.setEtapas(etapas);

        // Manejar imágenes
        Set<Imagen> imagenesExistentes = especimenExistente.getImagenes();
        for (Imagen imagenNueva : especimen.getImagenes()) {
            boolean existe = imagenesExistentes.stream()
                    .anyMatch(imagenExistente -> imagenExistente.getDireccion().equals(imagenNueva.getDireccion()));
            if (!existe) {
                imagenNueva.setEspecimen(especimenExistente); // Relación bidireccional
                imagenesExistentes.add(imagenNueva);
            }
        }



        especimenExistente.setImagenes(imagenesExistentes);


        // Manejar imágenes
        Set<Imagen3d> imagenes3dExistentes = especimenExistente.getImagenes3d();
        for (Imagen3d imagen3dNueva  : especimen.getImagenes3d()) {
            boolean existe = imagenes3dExistentes.stream()
                    .anyMatch(imagen3dExistente -> imagen3dExistente.getDireccion().equals(imagen3dNueva.getDireccion()));
            if (!existe) {
                imagen3dNueva.setEspecimen(especimenExistente); // Relación bidireccional
                imagenes3dExistentes.add(imagen3dNueva);
            }
        }

        especimenExistente.setImagenes3d(imagenes3dExistentes);

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
