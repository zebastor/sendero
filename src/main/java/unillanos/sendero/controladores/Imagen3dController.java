package unillanos.sendero.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unillanos.sendero.servicios.Imagen3dService;

@RestController
@RequestMapping("/imagen3d")
@CrossOrigin("*")
public class Imagen3dController {

    @Autowired
    private Imagen3dService imagen3dService;

    @DeleteMapping("/{id}")
    public void eliminarImagen3d(@PathVariable("id") Integer id){
        imagen3dService.eliminarImagen3d(id);
    }
}
