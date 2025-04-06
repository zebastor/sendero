package unillanos.sendero.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "etapas")
public class Etapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;

    @JsonIgnore
    @ManyToMany(mappedBy = "etapas") // ¡Usa mappedBy!
    private Set<Especimen> especimenes = new HashSet<>();

    public Etapa() {
    }

    public Etapa(int id, String nombre) {
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

    public Set<Especimen> getEspecimenes() {
        return especimenes;
    }

    public void setEspecimenes(Set<Especimen> especimenes) {
        this.especimenes = especimenes;
    }
}
