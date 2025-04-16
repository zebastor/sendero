package unillanos.sendero.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import unillanos.sendero.modelo.Reino;
import unillanos.sendero.modelo.Rol;
import unillanos.sendero.modelo.Usuario;
import unillanos.sendero.modelo.UsuarioRol;
import unillanos.sendero.servicios.UsuarioService;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/")
    public Usuario guardarUsuario(@RequestBody Usuario usuario) throws Exception{
        //usuario.setPerfil("default.png");
        usuario.setPassword(this.bCryptPasswordEncoder.encode(usuario.getPassword()));


        Set<UsuarioRol> usuarioRoles = new HashSet<>();

        Rol rol = new Rol();
        rol.setRolId(2L);
        rol.setRolNombre("NORMAL");

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);

        usuarioRoles.add(usuarioRol);
        return usuarioService.guardarUsuario(usuario,usuarioRoles);
    }


    @GetMapping("/{username}")
    public Usuario obtenerUsuario(@PathVariable("username") String username){
        return usuarioService.obtenerUsuario(username);
    }

    @DeleteMapping("/{usuarioId}")
    public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId){
        usuarioService.eliminarUsuario(usuarioId);
    }
//agregar actualizar obtenerlist

    @GetMapping("/")
    public ResponseEntity<?> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.obtenerUsuarios());
    }

    @PutMapping("/")
    public Usuario actualizarUsuario(@RequestBody Usuario usuario){
        return usuarioService.actualizarUsuario(usuario);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstadoUsuario(@PathVariable Long id, @RequestBody Map<String, Boolean> payload) {
        Boolean nuevoEstado = payload.get("enabled");
        Usuario usuarioActualizado = usuarioService.actualizarEstadoUsuario(id, nuevoEstado);

        if (usuarioActualizado != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
