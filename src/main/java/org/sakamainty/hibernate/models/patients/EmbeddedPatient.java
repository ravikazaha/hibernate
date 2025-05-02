package org.sakamainty.hibernate.models.patients;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmbeddedPatient {
    private List<Patient> patients;

    EmbeddedPatient() {
        patients = new ArrayList<>();
    }

    public List<Patient> getPatients() {
        return Collections.unmodifiableList(patients);
    }

    public void setPatients(List<Patient> patients) {
        this.patients = new ArrayList<>(patients != null ? patients : new ArrayList<>());
    }
}
