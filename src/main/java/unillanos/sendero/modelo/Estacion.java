package unillanos.sendero.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;




@Entity
@Table(name = "estaciones")
public class Estacion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numero;
    private String nombre;
    private String latitud;
    private String longitud;
    private String elementoInteractivo;




    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "estacion_especimen",
            joinColumns = @JoinColumn(name = "estacion_id"),
            inverseJoinColumns = @JoinColumn(name = "especimen_id")
    )
    private Set<Especimen> especimenes = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "estacion")
    private Set<Actividad> actividades = new HashSet<>();

    public Estacion(){

    }

    public Estacion(int id, int numero, String nombre, String latitud, String longitud) {
        this.id = id;
        this.numero = numero;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getElementoInteractivo() {
        return elementoInteractivo;
    }

    public void setElementoInteractivo(String elementoInteractivo) {
        this.elementoInteractivo = elementoInteractivo;
    }



    public Set<Especimen> getEspecimenes() {
        return especimenes;
    }

    public void setEspecimenes(Set<Especimen> especimenes) {
        this.especimenes = especimenes;
    }

    public Set<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(Set<Actividad> actividades) {
        this.actividades = actividades;
    }
}