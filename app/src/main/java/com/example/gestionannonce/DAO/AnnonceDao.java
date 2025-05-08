package com.example.gestionannonce.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestionannonce.Models.Annonce;

import java.util.List;

@Dao
public interface AnnonceDao {

    @Insert
    long insert(Annonce annonce);

    @Query("SELECT * FROM Annonce")
    List<Annonce> getAllAnnonces();

    @Query("SELECT * FROM Annonce WHERE titre LIKE :keyword")
    List<Annonce> searchAnnonces(String keyword);

    @Update
    int updateAnnonce(Annonce annonce);

    @Query("DELETE FROM Annonce WHERE id = :id")
    int deleteAnnonce(int id);

    // New method to get Annonce by ID
    @Query("SELECT * FROM Annonce WHERE id = :id")
    Annonce getAnnonceById(int id);
}
