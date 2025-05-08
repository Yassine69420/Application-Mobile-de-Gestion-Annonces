package com.example.gestionannonce.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gestionannonce.Models.Vendeur;

@Dao
public interface VendeurDao {

    @Insert
    long insert(Vendeur vendeur);

    @Query("SELECT * FROM Vendeur WHERE login = :login AND motDePasse = :motDePasse")
    Vendeur loginVendeur(String login, String motDePasse);

    @Query("SELECT * FROM Vendeur WHERE id = :id")
    Vendeur getVendeurById(int id);
}