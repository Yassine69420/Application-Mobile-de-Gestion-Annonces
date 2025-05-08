package com.example.gestionannonce.UI;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionannonce.Database.DatabaseHelper;
import com.example.gestionannonce.Models.Annonce;
import com.example.gestionannonce.R;

public class EditAnnonceActivity extends AppCompatActivity {

    private EditText titreEditText, prixEditText, categorieEditText, descriptionEditText;
    private Button saveBtn;
    private DatabaseHelper dbHelper;
    private Annonce annonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_annonce);

        dbHelper = new DatabaseHelper(this);

        titreEditText = findViewById(R.id.titreEditText);
        prixEditText = findViewById(R.id.prixEditText);
        categorieEditText = findViewById(R.id.categorieEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        saveBtn = findViewById(R.id.saveBtn);

        int annonceId = getIntent().getIntExtra("annonce_id", -1);
        if (annonceId == -1) {
            Toast.makeText(this, "Annonce non trouvée", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        annonce = getAnnonceById(annonceId);
        if (annonce == null) {
            Toast.makeText(this, "Annonce invalide", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Populate the form with the current annonce data
        titreEditText.setText(annonce.titre);
        prixEditText.setText(String.valueOf(annonce.prix));
        categorieEditText.setText(annonce.categorie);
        descriptionEditText.setText(annonce.description);

        saveBtn.setOnClickListener(v -> saveAnnonce());
    }

    private void saveAnnonce() {
        String titre = titreEditText.getText().toString().trim();
        String prixString = prixEditText.getText().toString().trim();
        String categorie = categorieEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (TextUtils.isEmpty(titre) || TextUtils.isEmpty(prixString) || TextUtils.isEmpty(categorie) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double prix = Double.parseDouble(prixString);

            // Update the annonce object with new data
            annonce.titre = titre;
            annonce.prix = prix;
            annonce.categorie = categorie;
            annonce.description = description;

            // Save the updated annonce to the database
            boolean updated = dbHelper.updateAnnonce(annonce);
            if (updated) {
                Toast.makeText(this, "Annonce mise à jour avec succès", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Échec de la mise à jour de l'annonce", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Veuillez entrer un prix valide", Toast.LENGTH_SHORT).show();
        }
    }


    private Annonce getAnnonceById(int id) {
        return dbHelper.getAnnonceById(id);
    }
}
