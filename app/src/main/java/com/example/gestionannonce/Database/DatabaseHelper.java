package com.example.gestionannonce.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gestionannonce.Models.Annonce;
import com.example.gestionannonce.Models.Vendeur;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "annonces.db";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Vendeur (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT, " +
                "email TEXT, " +
                "telephone TEXT, " +
                "login TEXT UNIQUE, " +
                "motDePasse TEXT)");

        db.execSQL("CREATE TABLE Annonce (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titre TEXT, " +
                "description TEXT, " +
                "prix REAL, " +
                "categorie TEXT, " +
                "date TEXT, " +
                "vendeurId INTEGER, " +
                "FOREIGN KEY(vendeurId) REFERENCES Vendeur(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Annonce");
        db.execSQL("DROP TABLE IF EXISTS Vendeur");
        onCreate(db);
    }

    // === Vendeur ===
    public boolean insertVendeur(String nom, String email, String telephone, String login, String motDePasse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("email", email);
        values.put("telephone", telephone);
        values.put("login", login);
        values.put("motDePasse", motDePasse);
        long result = db.insert("Vendeur", null, values);
        return result != -1;
    }

    public Vendeur loginVendeur(String login, String motDePasse) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Vendeur WHERE login = ? AND motDePasse = ?",
                new String[]{login, motDePasse}
        );

        if (cursor.moveToFirst()) {
            Vendeur vendeur = new Vendeur();
            vendeur.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            vendeur.nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
            vendeur.email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            vendeur.telephone = cursor.getString(cursor.getColumnIndexOrThrow("telephone"));
            vendeur.login = login;
            vendeur.motDePasse = motDePasse;
            cursor.close();
            return vendeur;
        }

        cursor.close();
        return null;
    }

    public boolean registerVendeur(String nom, String email, String telephone, String login, String motDePasse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("email", email);
        values.put("telephone", telephone);
        values.put("login", login);
        values.put("motDePasse", motDePasse);

        long result = db.insert("Vendeur", null, values);
        return result != -1;
    }

    // === Annonce ===
    public boolean insertAnnonce(String titre, String description, double prix, String categorie, String date, int vendeurId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titre", titre);
        values.put("description", description);
        values.put("prix", prix);
        values.put("categorie", categorie);
        values.put("date", date);
        values.put("vendeurId", vendeurId);
        long result = db.insert("Annonce", null, values);
        return result != -1;
    }

    public List<Annonce> getAllAnnonces() {
        List<Annonce> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Annonce", null);
        while (cursor.moveToNext()) {
            Annonce a = new Annonce();
            a.id = cursor.getInt(0);
            a.titre = cursor.getString(1);
            a.description = cursor.getString(2);
            a.prix = cursor.getDouble(3);
            a.categorie = cursor.getString(4);
            a.date = cursor.getString(5);
            a.vendeurId = cursor.getInt(6);
            list.add(a);
        }
        cursor.close();
        return list;
    }

    public boolean updateAnnonce(Annonce a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titre", a.titre);
        values.put("description", a.description);
        values.put("prix", a.prix);
        values.put("categorie", a.categorie);
        values.put("date", a.date);
        int rows = db.update("Annonce", values, "id = ?", new String[]{String.valueOf(a.id)});
        return rows > 0;
    }

    public boolean deleteAnnonce(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete("Annonce", "id = ?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    public List<Annonce> searchAnnonces(String keyword) {
        List<Annonce> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Annonce WHERE titre LIKE ?", new String[]{"%" + keyword + "%"});
        while (cursor.moveToNext()) {
            Annonce a = new Annonce();
            a.id = cursor.getInt(0);
            a.titre = cursor.getString(1);
            a.description = cursor.getString(2);
            a.prix = cursor.getDouble(3);
            a.categorie = cursor.getString(4);
            a.date = cursor.getString(5);
            a.vendeurId = cursor.getInt(6);
            list.add(a);
        }
        cursor.close();
        return list;
    }
}
