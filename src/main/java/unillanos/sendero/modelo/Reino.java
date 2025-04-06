package unillanos.sendero.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reinos")
public class Reino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "reino")
    @JsonIgnore
    private Set<Especimen> especimenes = new HashSet<>();

    public Reino() {
    }

    public Reino(int id, String nombre) {
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
