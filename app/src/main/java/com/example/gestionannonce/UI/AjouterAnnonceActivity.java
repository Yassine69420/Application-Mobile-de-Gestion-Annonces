package com.example.gestionannonce.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionannonce.Database.DatabaseHelper;
import com.example.gestionannonce.Models.Annonce;
import com.example.gestionannonce.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AjouterAnnonceActivity extends AppCompatActivity {

    private EditText inputTitre, inputDescription, inputPrix, inputCategorie;
    private Button btnAjouter;
    private DatabaseHelper dbHelper;
    private int vendeurId;

    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        vendeurId = sharedPreferences.getInt("userId", -1);

        if (vendeurId == -1) {
            Toast.makeText(this, "Utilisateur non connecté, veuillez vous connecter.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_ajouter_annonce);
        dbHelper = new DatabaseHelper(this);

        // Init UI components
        inputTitre = findViewById(R.id.inputTitre);
        inputDescription = findViewById(R.id.inputDescription);
        inputPrix = findViewById(R.id.inputPrix);
        inputCategorie = findViewById(R.id.inputCategorie);
        btnAjouter = findViewById(R.id.btnAjouterAnnonce);

        btnAjouter.setOnClickListener(v -> {
            String titre = inputTitre.getText().toString().trim();
            String description = inputDescription.getText().toString().trim();
            String prixText = inputPrix.getText().toString().trim();
            String categorie = inputCategorie.getText().toString().trim();

            if (titre.isEmpty() || description.isEmpty() || prixText.isEmpty() || categorie.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            double prix;
            try {
                prix = Double.parseDouble(prixText);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Prix invalide", Toast.LENGTH_SHORT).show();
                return;
            }

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            Annonce annonce = new Annonce(titre, description, prix, categorie, date, vendeurId);
            boolean inserted = dbHelper.insertAnnonce(annonce);

            if (inserted) {
                Toast.makeText(this, "Annonce ajoutée avec succès", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
