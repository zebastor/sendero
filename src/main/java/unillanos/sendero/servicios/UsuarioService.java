package unillanos.sendero.servicios;

import java.util.Set;

import unillanos.sendero.modelo.Reino;
import unillanos.sendero.modelo.Usuario;
import unillanos.sendero.modelo.UsuarioRol;

public interface UsuarioService {

    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception;

    public Usuario obtenerUsuario(String username);

    public void eliminarUsuario(Long usuarioId);
}