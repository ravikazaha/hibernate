package org.sakamainty.hibernate.models.patients;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.sakamainty.hibernate.models.Link;

import java.util.Map;

public class Patient {
    private String nom;
    private String prenom;
    private String sexe;
    private String adresse;
    @JsonProperty("_links") private Map<String, Link> links;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }
}
