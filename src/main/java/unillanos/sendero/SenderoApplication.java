package unillanos.sendero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import unillanos.sendero.modelo.Rol;
import unillanos.sendero.modelo.Usuario;
import unillanos.sendero.modelo.UsuarioRol;
import unillanos.sendero.servicios.UsuarioService;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class SenderoApplication /*implements CommandLineRunner*/ {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public static void main(String[] args) {		SpringApplication.run(SenderoApplication.class, args);}
/*
	@Override
	public void run(String... args) throws Exception {
		try{
			Usuario usuario = new Usuario();

			usuario.setNombre("seb");
			usuario.setUsername("seb");
			usuario.setPassword(bCryptPasswordEncoder.encode("123"));
			usuario.setEmail("alex@gmail.com");

			Rol rol = new Rol();
			rol.setRolId(1L);
			rol.setRolNombre("ADMIN");

			Set<UsuarioRol> usuariosRoles = new HashSet<>();
			UsuarioRol usuarioRol = new UsuarioRol();
			usuarioRol.setRol(rol);
			usuarioRol.setUsuario(usuario);
			usuariosRoles.add(usuarioRol);

			Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario,usuariosRoles);
			System.out.println(usuarioGuardado.getUsername());
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

*/
}
