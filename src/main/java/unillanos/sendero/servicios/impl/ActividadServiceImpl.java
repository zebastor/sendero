package unillanos.sendero.servicios.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unillanos.sendero.modelo.*;
import unillanos.sendero.repositorios.ActvidadRepository;
import unillanos.sendero.repositorios.ReinoRepository;
import unillanos.sendero.repositorios.UsuarioRepository;
import unillanos.sendero.servicios.ActividadService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActividadServiceImpl implements ActividadService {

    @Autowired
    private ActvidadRepository actvidadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Actividad agregarActividad(Actividad actividad) {

        Set<Usuario> usuarios = actividad.getUsuarios().stream()
                .map(usuario -> usuarioRepository.findById(usuario.getId())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrada")))
                .collect(Collectors.toSet());

        actividad.setUsuarios(usuarios);
        return actvidadRepository.save(actividad);
    }


@Override
        public Actividad actualizarActividad(Actividad actividad) {
            // Primero buscamos la actividad existente en la base de datos
            Actividad actividadExistente = actvidadRepository.findById(actividad.getId())
                    .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

            // Actualizamos los campos que necesitemos. Por ejemplo:
            actividadExistente.setTitulo(actividad.getTitulo());
            actividadExistente.setEstacion(actividad.getEstacion());
            // Aquí puedes actualizar otros campos que tenga Actividad...

            // Actualizamos las asociaciones: buscamos los usuarios existentes según el ID
            Set<Usuario> usuarios = actividad.getUsuarios().stream()
                    .map(usuario -> usuarioRepository.findById(usuario.getId())
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado")))
                    .collect(Collectors.toSet());
            actividadExistente.setUsuarios(usuarios);

            return actvidadRepository.save(actividadExistente);
        }



    @Override
    public Set<Actividad> obtenerActividades() {
        return new LinkedHashSet<>(actvidadRepository.findAll());
    }

    @Override
    public Actividad obtenerActividad(Integer id) {
        return actvidadRepository.findById(id).get();
    }

    @Override
    public void eliminarActividad(Integer id) {
        Actividad actividad = new Actividad();
        actividad.setId(id);
        actvidadRepository.delete(actividad);
    }
}
