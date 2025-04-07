package unillanos.sendero.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unillanos.sendero.modelo.Actividad;
import unillanos.sendero.modelo.Reino;
import unillanos.sendero.servicios.ActividadService;
import unillanos.sendero.servicios.ReinoService;

@RestController
@RequestMapping("/actividad")
@CrossOrigin("*")
public class ActividadController {
    @Autowired
    private ActividadService actividadService;

    @PostMapping("/")
    public ResponseEntity<Actividad> guardarActividad(@RequestBody Actividad actividad){
        Actividad actividadGuardada = actividadService.agregarActividad(actividad);
        return ResponseEntity.ok(actividadGuardada);
    }

    @GetMapping("/{id}")
    public Actividad listarActividadPorId(@PathVariable("id") Integer id){
        return actividadService.obtenerActividad(id);
    }


    @GetMapping("/")
    public ResponseEntity<?> listarActividades(){
        return ResponseEntity.ok(actividadService.obtenerActividades());
    }

    @PutMapping("/")
    public Actividad actualizarActividad(@RequestBody Actividad actividad){
        return actividadService.actualizarActividad(actividad);
    }

    @DeleteMapping("/{id}")
    public void eliminarActividad(@PathVariable("id") Integer id){
        actividadService.eliminarActividad(id);
    }
}
