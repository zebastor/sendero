package unillanos.sendero.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "especimenes")
public class Especimen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "especimen_etapa",
            joinColumns = @JoinColumn(name = "especimen_id"),
            inverseJoinColumns = @JoinColumn(name = "etapa_id")
    )
    private Set<Etapa> etapas = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "especimen")
    private Set<Imagen> imagenes = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "especimenes") // ¡Usa mappedBy!
    private Set<Estacion> estaciones = new HashSet<>();


    @ManyToOne(fetch = FetchType.EAGER)
    private Reino reino;


    public Especimen() {
    }

    public Especimen(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Etapa> getEtapas() {
        return etapas;
    }

    public void setEtapas(Set<Etapa> etapas) {
        this.etapas = etapas;
    }

    public Set<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(Set<Imagen> imagenes) {
        this.imagenes = imagenes;
    }


    public Set<Estacion> getEstaciones() {
        return estaciones;
    }

    public void setEstaciones(Set<Estacion> estaciones) {
        this.estaciones = estaciones;
    }

    public Reino getReino() {
        return reino;
    }

    public void setReino(Reino reino) {
        this.reino = reino;
    }


}
