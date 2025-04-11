package unillanos.sendero.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unillanos.sendero.modelo.Especimen;
import unillanos.sendero.servicios.EspecimenService;
import unillanos.sendero.servicios.ImagenService;


@RestController
@RequestMapping("/especimen")
@CrossOrigin("*")
public class EspecimenController {


    @Autowired
    private EspecimenService especimenService;

    @Autowired
    private ImagenService imagenService;


    @PostMapping("/")
    public ResponseEntity<Especimen> guardarEspecimen(@RequestBody Especimen especimen){
        Especimen especimenGuardada = especimenService.agregarEspecimen(especimen);
        return ResponseEntity.ok(especimenGuardada);
    }


    @GetMapping("/")
    public ResponseEntity<?> listarEspecimenes() {
        return ResponseEntity.ok(especimenService.obtenerEspecimenes());
    }


    @GetMapping("/{id}")
    public Especimen listarEspecimenPorId(@PathVariable("id") Integer id){
        return especimenService.obtenerEspecimen(id);
    }


    @PutMapping("/")
    public Especimen actualizarEspecimen(@RequestBody Especimen especimen){
        return especimenService.actualizarEspecimen(especimen);
    }

    @DeleteMapping("/{id}")
    public void eliminarEspecimen(@PathVariable("id") Integer id){
        especimenService.eliminarEspecimen(id);
    }

}
