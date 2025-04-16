package unillanos.sendero.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unillanos.sendero.modelo.Especimen;
import unillanos.sendero.modelo.Estacion;
import unillanos.sendero.modelo.Etapa;
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


    @GetMapping("/{id}")
    public Estacion listarEstacionPorId(@PathVariable("id") Integer id){
        return estacionService.obtenerEstacion(id);
    }
    @GetMapping("/numero/{numero}")
    public Estacion listarEstacionPorNumero(@PathVariable("numero") Integer numero){
        return estacionService.obtenerEstacionPorNumero(numero);
    }


    @PutMapping("/")
    public Estacion actualizarEstacion(@RequestBody Estacion estacion){
        return estacionService.actualizarEstacion(estacion);
    }

    @DeleteMapping("/{id}")
    public void eliminarEstacion(@PathVariable("id") Integer id){
        estacionService.eliminarEstacion(id);
    }

}
