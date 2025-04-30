package org.sakamainty.hibernate.models;
import java.util.List;

public class Embedded {
    private List<Medecin> medecins;

    public List<Medecin> getMedecins() {
        return medecins;
    }

    public void setMedecins(List<Medecin> medecins) {
        this.medecins = medecins;
    }
}