package unillanos.sendero.controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unillanos.sendero.modelo.Reino;
import unillanos.sendero.servicios.EtapaService;
import unillanos.sendero.modelo.Etapa;
import unillanos.sendero.repositorios.EtapaRepository;

@RestController
@RequestMapping("/etapa")
@CrossOrigin("*")
public class EtapaController {

    @Autowired
    private EtapaService etapaService;

    @PostMapping("/")
    public ResponseEntity<Etapa> guardarEtapa(@RequestBody Etapa etapa){
        Etapa etapaGuardada = etapaService.agregarEtapa(etapa);
        return ResponseEntity.ok(etapaGuardada);
    }

    @GetMapping("/")
    public ResponseEntity<?> listarEtapas(){
        return ResponseEntity.ok(etapaService.obtenerEtapas());
    }


    @GetMapping("/{id}")
    public Etapa listarEtapaPorId(@PathVariable("id") Integer id){
        return etapaService.obtenerEtapa(id);
    }


    @PutMapping("/")
    public Etapa actualizarEtapa(@RequestBody Etapa etapa){
        return etapaService.actualizarEtapa(etapa);
    }

    @DeleteMapping("/{id}")
    public void eliminarEtapa(@PathVariable("id") Integer id){
        etapaService.eliminarEtapa(id);
    }
}
