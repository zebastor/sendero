package unillanos.sendero.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unillanos.sendero.modelo.*;
import unillanos.sendero.repositorios.*;
import unillanos.sendero.servicios.EspecimenService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Transactional
    public void eliminarEspecimen(Integer id) {
        Especimen especimen = especimenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espécimen no encontrado con ID: " + id));

        // Eliminar imágenes de la base de datos y del sistema de archivos
        if (especimen.getImagenes() != null) {
            for (Imagen imagen : especimen.getImagenes()) {
                imagenRepository.delete(imagen);
                eliminarArchivo("uploads", imagen.getDireccion()); // Nuevo método
            }
        }

        // Eliminar imágenes 3D de la base de datos y del sistema de archivos
        if (especimen.getImagenes3d() != null) {
            for (Imagen3d imagen3d : especimen.getImagenes3d()) {
                imagen3dRepository.delete(imagen3d);
                eliminarArchivo("uploads3d", imagen3d.getDireccion()); // Nuevo método
            }
        }
        for (Estacion estacion : especimen.getEstaciones()) {
            estacion.getEspecimenes().remove(especimen);
        }

        especimen.getEstaciones().clear(); // Por seguridad

        especimenRepository.delete(especimen);
        // Eliminar relaciones y el espécimen
        especimen.getEtapas().clear();
        especimen.setReino(null);
        especimenRepository.delete(especimen);
    }

    // Método auxiliar para eliminar archivos
    private void eliminarArchivo(String directorio, String nombreArchivo) {
        try {
            Path filePath = Paths.get(directorio).resolve(nombreArchivo);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Registrar el error (p.ej., con un logger)
            System.err.println("Error al eliminar el archivo: " + nombreArchivo);
            e.printStackTrace();
        }
    }

}
