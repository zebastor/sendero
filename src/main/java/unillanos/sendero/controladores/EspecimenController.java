package unillanos.sendero.controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unillanos.sendero.modelo.Especimen;
import unillanos.sendero.modelo.Reino;
import unillanos.sendero.servicios.EspecimenService;

import java.util.Set;

@RestController
@RequestMapping("/especimen")
@CrossOrigin("*")
public class EspecimenController {
    @Autowired
    private EspecimenService especimenService;

    @PostMapping("/")
    public ResponseEntity<Especimen> guardarEspecimen(@RequestBody Especimen especimen){
        Especimen especimenGuardada = especimenService.agregarEspecimen(especimen);
        return ResponseEntity.ok(especimenGuardada);
    }


    @GetMapping("/")
    public Set<Especimen> listarEspecimenes() {
        // Si algunas relaciones son lazy, considera forzar su inicialización o usar DTOs.
        return especimenService.obtenerEspecimenes();
    }

}
