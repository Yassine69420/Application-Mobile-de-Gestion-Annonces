package com.example.gestionannonce.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gestionannonce.DAO.AnnonceDao;
import com.example.gestionannonce.DAO.VendeurDao;
import com.example.gestionannonce.Models.Annonce;
import com.example.gestionannonce.Models.Vendeur;

@Database(entities = {Vendeur.class, Annonce.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract VendeurDao vendeurDao();
    public abstract AnnonceDao annonceDao();
}
