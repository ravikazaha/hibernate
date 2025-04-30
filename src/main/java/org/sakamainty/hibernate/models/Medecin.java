package org.sakamainty.hibernate.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Medecin {
    private String nom;
    private String prenom;
    private String grade;

    @JsonProperty("_links")
    private Map<String, Link> links;

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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }
}
