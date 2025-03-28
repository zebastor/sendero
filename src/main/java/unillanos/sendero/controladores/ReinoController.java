package unillanos.sendero.controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unillanos.sendero.modelo.Reino;
import unillanos.sendero.servicios.ReinoService;

@RestController
@RequestMapping("/reino")
@CrossOrigin("*")
public class ReinoController {
    @Autowired
    private ReinoService reinoService;

    @PostMapping("/")
    public ResponseEntity<Reino> guardarReino(@RequestBody Reino reino){
        Reino reinoGuardada = reinoService.agregarReino(reino);
        return ResponseEntity.ok(reinoGuardada);
    }

    @GetMapping("/{id}")
    public Reino listarReinoPorId(@PathVariable("id") Integer id){
        return reinoService.obtenerReino(id);
    }


    @GetMapping("/")
    public ResponseEntity<?> listarReinos(){
        return ResponseEntity.ok(reinoService.obtenerReinos());
    }

    @PutMapping("/")
    public Reino actualizarReino(@RequestBody Reino reino){
        return reinoService.actualizarReino(reino);
    }

    @DeleteMapping("/{id}")
    public void eliminarReino(@PathVariable("id") Integer id){
        reinoService.eliminarReino(id);
    }
}
