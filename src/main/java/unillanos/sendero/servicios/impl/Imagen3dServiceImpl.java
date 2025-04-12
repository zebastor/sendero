package unillanos.sendero.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unillanos.sendero.modelo.Imagen;
import unillanos.sendero.modelo.Imagen3d;
import unillanos.sendero.repositorios.Imagen3dRepository;
import unillanos.sendero.repositorios.ImagenRepository;
import unillanos.sendero.servicios.Imagen3dService;

@Service
public class Imagen3dServiceImpl implements Imagen3dService {
    @Autowired
    private Imagen3dRepository imagen3dRepository;

    @Override
    public void eliminarImagen3d(Integer id) {
        Imagen3d imagen3d = new Imagen3d();
        imagen3d.setId(id);
        imagen3dRepository.delete(imagen3d);
    }
}
