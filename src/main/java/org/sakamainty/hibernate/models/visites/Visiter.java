package org.sakamainty.hibernate.models.visites;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.sakamainty.hibernate.models.Link;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Visiter {
    @JsonProperty("date") private String date;
    @JsonProperty("_links") private Map<String, Link> links;

    @JsonProperty("medecin")
    private String medecin;

    @JsonProperty("patient")
    private String patient;

    public String getDate() {
        return LocalDateTime.parse(this.date).toString();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public String getMedecin() {
        return medecin;
    }

    public void setMedecin(String medecinRef) {
        this.medecin = medecinRef;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patientRef) {
        this.patient = patientRef;
    }
}
