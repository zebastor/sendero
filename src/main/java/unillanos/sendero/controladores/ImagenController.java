package unillanos.sendero.controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unillanos.sendero.modelo.Especimen;
import unillanos.sendero.modelo.Etapa;
import unillanos.sendero.modelo.Imagen;
import unillanos.sendero.servicios.EspecimenService;
import unillanos.sendero.servicios.ImagenService;

@RestController
@RequestMapping("/imagen")
@CrossOrigin("*")
public class ImagenController {

    @Autowired
    private ImagenService imagenService;

    @PostMapping("/")
    public ResponseEntity<Imagen> guardarImagen(@RequestBody Imagen imagen){
        Imagen imagenGuardada = imagenService.agregarImagen(imagen);
        return ResponseEntity.ok(imagenGuardada);
    }


    @GetMapping("/")
    public ResponseEntity<?> listarImagenes() {
        return ResponseEntity.ok(imagenService.obtenerImagenes());
    }


    @GetMapping("/{id}")
    public Imagen listarImagenPorId(@PathVariable("id") Integer id){
        return imagenService.obtenerImagen(id);
    }


    @PutMapping("/")
    public Imagen actualizarImagen(@RequestBody Imagen imagen){
        return imagenService.actualizarImagen(imagen);
    }

    @DeleteMapping("/{id}")
    public void eliminarImagen(@PathVariable("id") Integer id){
        imagenService.eliminarImagen(id);
    }
}
