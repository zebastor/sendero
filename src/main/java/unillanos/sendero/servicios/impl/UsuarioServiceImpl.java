package unillanos.sendero.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unillanos.sendero.modelo.Reino;
import unillanos.sendero.modelo.Usuario;
import unillanos.sendero.repositorios.RolRepository;
import unillanos.sendero.repositorios.UsuarioRepository;
import unillanos.sendero.servicios.UsuarioService;
import unillanos.sendero.modelo.UsuarioRol;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
        Usuario usuarioLocal = usuarioRepository.findByUsername(usuario.getUsername());
        if(usuarioLocal != null){
            System.out.println("El usuario ya existe");
            throw new Exception("El usuario ya esta presente");
        }
        else{
            for(UsuarioRol usuarioRol:usuarioRoles){
                rolRepository.save(usuarioRol.getRol());
            }
            usuario.getUsuarioRoles().addAll(usuarioRoles);
            usuarioLocal = usuarioRepository.save(usuario);
        }
        return usuarioLocal;
    }

    @Override
    public Usuario obtenerUsuario(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public void eliminarUsuario(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

    @Override
    public Usuario agregarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


    @Override
    public Usuario obtenerUsuariox(Long id) {
        return usuarioRepository.findById(id).get();
    }

    @Override
    public Set<Usuario> obtenerUsuarios() {
        return new LinkedHashSet<>(usuarioRepository.findAll());
    }



    @Override
    public Usuario guardarUsuariox(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
        Usuario usuarioLocal = usuarioRepository.findByUsername(usuario.getUsername());

            for(UsuarioRol usuarioRol:usuarioRoles){
                rolRepository.save(usuarioRol.getRol());
            }
            usuario.getUsuarioRoles().addAll(usuarioRoles);
            usuarioLocal = usuarioRepository.save(usuario);

        return usuarioLocal;
    }

    @Override
    public Usuario actualizarEstadoUsuario(Long id, boolean enabled) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setEnabled(enabled);
            return usuarioRepository.save(usuario);
        }
        return null;
    }

}