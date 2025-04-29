package org.sakamainty.hibernate;

import java.time.LocalDate;

public class Visite {
    private Patient patient;
    private Medecin medecin;
    private LocalDate date;

    public Visite(Patient patient, Medecin medecin, LocalDate date) {
        this.patient = patient;
        this.medecin = medecin;
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
