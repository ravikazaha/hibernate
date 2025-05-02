package org.sakamainty.hibernate.models.medecins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmbeddedMedecin {
    private List<Medecin> medecins;

    public EmbeddedMedecin() {
        this.medecins = new ArrayList<>();
    }

    public List<Medecin> getMedecins() {
        return Collections.unmodifiableList(medecins);
    }

    public void setMedecins(List<Medecin> medecins) {
        this.medecins = new ArrayList<>(medecins != null ? medecins : new ArrayList<>());
    }
}