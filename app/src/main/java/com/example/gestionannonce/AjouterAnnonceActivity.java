package com.example.gestionannonce;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionannonce.Database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AjouterAnnonceActivity extends AppCompatActivity {

    private EditText inputTitre, inputDescription, inputPrix, inputCategorie;
    private Button btnAjouter;
    private DatabaseHelper dbHelper;
    private int vendeurId; // Dynamically fetched from shared preferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_annonce);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Fetch the current vendeurId from SharedPreferences (assuming it was saved during login)
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        vendeurId = sharedPreferences.getInt("vendeur_id", -1); // Default to -1 if no vendeurId is found

        // If no vendeurId is found, ask the user to log in (optional step, based on your app flow)
        if (vendeurId == -1) {
            Toast.makeText(this, "Utilisateur non connecté, veuillez vous connecter.", Toast.LENGTH_SHORT).show();
            finish(); // Exit the activity if the user is not logged in
            return;
        }

        // Initialize the input fields and button
        inputTitre = findViewById(R.id.inputTitre);
        inputDescription = findViewById(R.id.inputDescription);
        inputPrix = findViewById(R.id.inputPrix);
        inputCategorie = findViewById(R.id.inputCategorie);
        btnAjouter = findViewById(R.id.btnAjouterAnnonce);

        // Button click listener to add a new annonce
        btnAjouter.setOnClickListener(v -> {
            String titre = inputTitre.getText().toString().trim();
            String description = inputDescription.getText().toString().trim();
            String prixText = inputPrix.getText().toString().trim();
            String categorie = inputCategorie.getText().toString().trim();

            // Check if any input field is empty
            if (titre.isEmpty() || description.isEmpty() || prixText.isEmpty() || categorie.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse the price field to a double
            double prix;
            try {
                prix = Double.parseDouble(prixText);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Prix invalide", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get the current date
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            // Insert the new annonce into the database
            boolean inserted = dbHelper.insertAnnonce(titre, description, prix, categorie, date, vendeurId);
            if (inserted) {
                Toast.makeText(this, "Annonce ajoutée avec succès", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
