package unillanos.sendero.controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import unillanos.sendero.modelo.Rol;
import unillanos.sendero.modelo.Usuario;
import unillanos.sendero.modelo.UsuarioRol;
import unillanos.sendero.servicios.UsuarioService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/usuario")
public class UserController {


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/{username}")
    public Usuario obtenerUsuario(@PathVariable("username") String username){
        return usuarioService.obtenerUsuario(username);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable("id") Long id){
        usuarioService.eliminarUsuario(id);
    }
//agregar actualizar obtenerlist

    @GetMapping("/")
    public ResponseEntity<?> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.obtenerUsuarios());
    }

    @PutMapping("/")
    public Usuario guardarUsuariox(@RequestBody Usuario usuario) throws Exception {
        // Primero, obtener el usuario actual de la base de datos (por ID)
        Usuario usuarioExistente = usuarioService.obtenerUsuariox(usuario.getId());

        // Actualiza los datos básicos
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setUsername(usuario.getUsername());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

        // Limpia la colección de roles existentes para evitar duplicados
        usuarioExistente.getUsuarioRoles().clear();

        // Obtén el rolId del request o usa el valor por defecto (2L)
        Long rolId = usuario.getRolId() != null ? usuario.getRolId() : 2L;

        // Crea el rol según el rolId recibido
        Rol rol = new Rol();
        rol.setRolId(rolId);
        if (rolId.equals(1L)) {
            rol.setRolNombre("ADMIN");
        } else {
            rol.setRolNombre("NORMAL");
        }

        // Crea el objeto UsuarioRol con el usuario existente y el nuevo rol
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuarioExistente);
        usuarioRol.setRol(rol);

        // Agrega el único rol al conjunto del usuario existente
        usuarioExistente.getUsuarioRoles().add(usuarioRol);

        // Guarda el usuario actualizado, reemplazando los roles anteriores
        return usuarioService.guardarUsuariox(usuarioExistente, usuarioExistente.getUsuarioRoles());
    }






}
