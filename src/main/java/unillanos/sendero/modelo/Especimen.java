package unillanos.sendero.modelo;

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

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "especimen")
    private Set<EspecimenEtapa> especimenEtapas = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "especimen")
    private Set<Imagen> imagenes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "especimen")
    private Set<EspecimenEstacion> especimenEstaciones = new HashSet<>();


    @ManyToOne
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

    public Set<EspecimenEtapa> getEspecimenEtapas() {
        return especimenEtapas;
    }

    public void setEspecimenEtapas(Set<EspecimenEtapa> especimenEtapas) {
        this.especimenEtapas = especimenEtapas;
    }

    public Set<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(Set<Imagen> imagenes) {
        this.imagenes = imagenes;
    }


    public Set<EspecimenEstacion> getEspecimenEstaciones() {
        return especimenEstaciones;
    }

    public void setEspecimenEstaciones(Set<EspecimenEstacion> especimenEstaciones) {
        this.especimenEstaciones = especimenEstaciones;
    }

    public Reino getReino() {
        return reino;
    }

    public void setReino(Reino reino) {
        this.reino = reino;
    }


}
