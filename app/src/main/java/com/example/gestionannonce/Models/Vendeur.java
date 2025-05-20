package com.example.gestionannonce.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Vendeur")
public class Vendeur {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nom")
    public String nom;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "telephone")
    public String telephone;

    @ColumnInfo(name = "login")
    public String login;

    @ColumnInfo(name = "motDePasse")
    public String motDePasse;


    @Ignore
    public Vendeur(String nom, String email, String telephone, String login, String motDePasse) {
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.login = login;
        this.motDePasse = motDePasse;
    }

    public Vendeur() {
    }
}
