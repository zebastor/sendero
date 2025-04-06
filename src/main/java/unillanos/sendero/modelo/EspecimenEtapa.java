package unillanos.sendero.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

//@Entity
public class EspecimenEtapa {
   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int especimenEtapaId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference("especimen-etapa")
    private Especimen especimen;

    @ManyToOne
    @JsonBackReference("especimen-etapa")
    private Etapa etapa;

    public int getEspecimenEtapaId() {
        return especimenEtapaId;
    }

    public void setEspecimenEtapaId(int especimenEtapaId) {
        this.especimenEtapaId = especimenEtapaId;
    }

    public Especimen getEspecimen() {
        return especimen;
    }

    public void setEspecimen(Especimen especimen) {
        this.especimen = especimen;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }*/
}
