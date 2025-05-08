package com.example.gestionannonce.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Annonce",
        foreignKeys = @ForeignKey(
                entity = Vendeur.class,
                parentColumns = "id",
                childColumns = "vendeurId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Annonce {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "titre")
    public String titre;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "prix")
    public double prix;

    @ColumnInfo(name = "categorie")
    public String categorie;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "vendeurId", index = true)
    public int vendeurId;

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
