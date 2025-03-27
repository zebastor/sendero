package unillanos.sendero.modelo;


import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    private int id;
    private String titulo;
    private Date fecha;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "actividad")
    private Set<UsuarioActividad> usuarioActividades = new HashSet<>();

    public Actividad() {}

    public Actividad(int id, String titulo, Date fecha) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
    }

    public int getId() {        return id;}

    public void setId(int id) {        this.id = id;}

    public String getTitulo() {        return titulo;}

    public void setTitulo(String titulo) {        this.titulo = titulo;}

    public Date getFecha() {        return fecha;}

    public void setFecha(Date fecha) {        this.fecha = fecha;}


    public Set<UsuarioActividad> getUsuarioActividades() {
        return usuarioActividades;
    }

    public void setUsuarioActividades(Set<UsuarioActividad> usuarioActividades) {
        this.usuarioActividades = usuarioActividades;
    }
}
