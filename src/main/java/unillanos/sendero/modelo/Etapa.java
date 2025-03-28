package unillanos.sendero.modelo;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "etapas")
public class Etapa {
    private int id;
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "etapa")
    private Set<EspecimenEtapa> especimenEtapas = new HashSet<>();

    public Etapa() {
    }

    public Etapa(int id, String nombre, Set<EspecimenEtapa> especimenEtapas) {
        this.id = id;
        this.nombre = nombre;
        this.especimenEtapas = especimenEtapas;
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
}
