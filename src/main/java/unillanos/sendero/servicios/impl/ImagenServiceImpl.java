package unillanos.sendero.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unillanos.sendero.modelo.Etapa;
import unillanos.sendero.modelo.Imagen;
import unillanos.sendero.repositorios.EtapaRepository;
import unillanos.sendero.repositorios.ImagenRepository;
import unillanos.sendero.servicios.ImagenService;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class ImagenServiceImpl implements ImagenService {

    @Autowired
    private ImagenRepository imagenRepository;

    @Override
    public Imagen agregarImagen(Imagen imagen) {
        return imagenRepository.save(imagen);
    }

    @Override
    public Imagen actualizarImagen(Imagen imagen) {
        return imagenRepository.save(imagen);
    }

    @Override
    public Set<Imagen> obtenerImagenes() {
        return new LinkedHashSet<>(imagenRepository.findAll());
    }

    @Override
    public Imagen obtenerImagen(Integer id) {
        return imagenRepository.findById(id).get();
    }

    @Override
    public void eliminarImagen(Integer id) {
        Imagen imagen = new Imagen();
        imagen.setId(id);
        imagenRepository.delete(imagen);
    }
}
