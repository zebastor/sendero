package unillanos.sendero.modelo;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private Date fecha;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "actividad_usuario",
            joinColumns = @JoinColumn(name = "actividad_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )// Serializa desde aquí
    private Set<Usuario> usuarios = new HashSet<>();


    @ManyToOne
    private Estacion estacion;

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

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }
}
