package com.company.Entity;

public class Materiel {

    private final String identifiant;
    private final String nom;

    public Materiel(String identifiant, String nom) {
        this.identifiant = identifiant;
        this.nom = nom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Materiel{" +
                "identifiant='" + identifiant + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }

}
