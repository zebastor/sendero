package unillanos.sendero.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "imagenes")
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String direccion;

    @ManyToOne(fetch = FetchType.EAGER)
    private Especimen especimen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Especimen getEspecimen() {
        return especimen;
    }

    public void setEspecimen(Especimen especimen) {
        this.especimen = especimen;
    }
}
