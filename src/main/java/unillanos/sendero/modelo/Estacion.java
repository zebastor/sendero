package unillanos.sendero.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;
    private int numero;
    private String nombre;
    private String latitud;
    private String longitud;
    private String elementoInteractivo;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "estacion")
    private Set<EspecimenEstacion> especimenEstaciones = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "estacion")
    private Set<Actividad> actividades = new HashSet<>();

    public Estacion(){

    }

    public Estacion(Long id, int numero, String nombre, String latitud, String longitud) {
        this.id = id;
        this.numero = numero;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<EspecimenEstacion> getEspecimenEstaciones() {
        return especimenEstaciones;
    }

    public void setEspecimenEstaciones(Set<EspecimenEstacion> especimenEstaciones) {
        this.especimenEstaciones = especimenEstaciones;
    }

    public Set<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(Set<Actividad> actividades) {
        this.actividades = actividades;
    }
}