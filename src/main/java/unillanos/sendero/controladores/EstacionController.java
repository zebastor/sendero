package unillanos.sendero.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unillanos.sendero.modelo.Especimen;
import unillanos.sendero.modelo.Estacion;
import unillanos.sendero.servicios.EspecimenService;
import unillanos.sendero.servicios.EstacionService;

@RestController
@RequestMapping("/estacion")
@CrossOrigin("*")
public class EstacionController {

    @Autowired
    private EstacionService estacionService;

    @PostMapping("/")
    public ResponseEntity<Estacion> guardarEstacion(@RequestBody Estacion estacion){
        Estacion estacionGuardada = estacionService.agregarEstacion(estacion);
        return ResponseEntity.ok(estacionGuardada);
    }


    @GetMapping("/")
    public ResponseEntity<?> listarEstaciones() {
        return ResponseEntity.ok(estacionService.obtenerEstaciones());
    }

}
