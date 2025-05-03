package com.example.gestionannonce.Models;


public class Annonce {
    public int id;
    public String titre;
    public String description;
    public double prix;
    public String categorie;
    public String date;
    public int vendeurId; // Clé étrangère vers Vendeur

    public Annonce(String titre, String description, double prix, String categorie, String date, int vendeurId) {
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.categorie = categorie;
        this.date = date;
        this.vendeurId = vendeurId;
    }

    public Annonce() {

    }
}
