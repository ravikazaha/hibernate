package org.sakamainty.hibernate.models.visites;

import org.sakamainty.hibernate.models.patients.Patient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmbeddedVisiter {
    private List<Visiter> visiters;

    EmbeddedVisiter() {
        visiters = new ArrayList<>();
    }

    public List<Visiter> getVisiters() {
        return Collections.unmodifiableList(visiters);
    }

    public void setPatients(List<Visiter> visiters) {
        this.visiters = new ArrayList<>(visiters != null ? visiters : new ArrayList<>());
    }
}

