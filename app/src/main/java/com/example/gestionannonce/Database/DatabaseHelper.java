package com.example.gestionannonce.Database;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gestionannonce.Models.Annonce;
import com.example.gestionannonce.Models.Vendeur;

import java.util.List;

public class DatabaseHelper {

    private AppDatabase db;
    private SharedPreferences sharedPreferences;

    public DatabaseHelper(Context context) {
        db = DatabaseClient.getInstance(context).getAppDatabase();
        sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
    }

    //================================= Vendeur operations ======================================
    public boolean registerVendeur(Vendeur vendeur) {
        long result = db.vendeurDao().insert(vendeur);
        return result != -1;
    }

    public Vendeur loginVendeur(String login, String motDePasse) {
        return db.vendeurDao().loginVendeur(login, motDePasse);
    }

    public Vendeur getVendeurById(int id) {
        return db.vendeurDao().getVendeurById(id);
    }

    //================================= Annonce operations =====================================
    public boolean insertAnnonce(Annonce annonce) {
        long result = db.annonceDao().insert(annonce);
        return result != -1;
    }

    public List < Annonce > getAllAnnonces() {
        return db.annonceDao().getAllAnnonces();
    }

    public boolean updateAnnonce(Annonce annonce) {
        int rows = db.annonceDao().updateAnnonce(annonce);
        return rows > 0;
    }

    public boolean deleteAnnonce(int id) {
        int rows = db.annonceDao().deleteAnnonce(id);
        return rows > 0;
    }

    public List < Annonce > searchAnnonces(String keyword) {
        return db.annonceDao().searchAnnonces(keyword);
    }
    // New method to get Annonce by ID
    public Annonce getAnnonceById(int id) {
        return db.annonceDao().getAnnonceById(id);
    }
}