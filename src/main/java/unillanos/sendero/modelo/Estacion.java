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
    private Set<Especimen> especimenes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "especimen")
    private Set<Imagen> imagenes = new HashSet<>();

    public Estacion(){

    }

    public Estacion(Long id, int numero, String nombre, String latitud, String longitud) {
        this.id = id;
        this.numero = numero;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }



}