package org.sakamainty.hibernate;

public class Medecin {
    private String nom;
    private String prenom;
    private String grade;
    private String codemed;

    public Medecin(String nom, String prenom, String grade) {
        this.nom = nom;
        this.prenom = prenom;
        this.grade = grade;
    }

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

    public String getCodemed() {
        return codemed;
    }

    public void setCodemed(String codemed) {
        this.codemed = codemed;
    }
}
