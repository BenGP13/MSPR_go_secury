package com.company.Entity;

import java.util.List;

public class Agent {

    private String nom;
    private String prenom;
    private String mission;
    private String motDePasse;
    private List<Materiel> listMateriels;

    public Agent() {}

    public Agent(String nom, String prenom, String mission, String motDePasse, List<Materiel> listMateriels) {
        this.nom = nom;
        this.prenom = prenom;
        this.mission = mission;
        this.motDePasse = motDePasse;
        this.listMateriels = listMateriels;
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

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public List<Materiel> getListMateriels() {
        return listMateriels;
    }

    public void setListMateriels(List<Materiel> listMateriels) {
        this.listMateriels = listMateriels;
    }

    public void addMateriel(Materiel materiel){
        this.listMateriels.add(materiel);
    }

    @Override
    public String toString() {
        return "Agent{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mission='" + mission + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", listMateriels=" + listMateriels +
                '}';
    }

}
