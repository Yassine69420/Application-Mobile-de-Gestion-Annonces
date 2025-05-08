package com.example.gestionannonce.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionannonce.Database.DatabaseHelper;
import com.example.gestionannonce.Models.Annonce;
import com.example.gestionannonce.Models.Vendeur;
import com.example.gestionannonce.R;

public class AnnonceDetailActivity extends AppCompatActivity {

    private TextView titreTextView, prixTextView, categorieTextView,
            descriptionTextView, dateTextView, vendeurTextView;
    private Button appelBtn, smsBtn, emailBtn, editBtn, deleteBtn;
    private DatabaseHelper dbHelper;

    private Annonce annonce;
    private Vendeur vendeur;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_detail);

        // Initialisation
        dbHelper = new DatabaseHelper(this);
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        currentUserId = prefs.getInt("userId", -1);

        titreTextView       = findViewById(R.id.titreTextView);
        prixTextView        = findViewById(R.id.prixTextView);
        categorieTextView   = findViewById(R.id.categorieTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        dateTextView        = findViewById(R.id.dateTextView);
        vendeurTextView     = findViewById(R.id.vendeurTextView);
        appelBtn            = findViewById(R.id.appelBtn);
        smsBtn              = findViewById(R.id.smsBtn);
        emailBtn            = findViewById(R.id.emailBtn);
        editBtn             = findViewById(R.id.editBtn);
        deleteBtn           = findViewById(R.id.deleteBtn);

        int annonceId = getIntent().getIntExtra("annonce_id", -1);
        if (annonceId == -1) {
            Toast.makeText(this, "Annonce non trouvée", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        annonce = dbHelper.getAnnonceById(annonceId);
        if (annonce == null) {
            Toast.makeText(this, "Annonce invalide", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        vendeur = dbHelper.getVendeurById(annonce.vendeurId);

        updateUI();

        // Ternaire pour afficher ou masquer editBtn et deleteBtn
        int visibility = (vendeur != null && vendeur.id == currentUserId)
                ? View.VISIBLE
                : View.GONE;
        editBtn.setVisibility(visibility);
        deleteBtn.setVisibility(visibility);

        appelBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + vendeur.telephone));
            startActivity(intent);
        });

        smsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("sms:" + vendeur.telephone));
            intent.putExtra("sms_body",
                    "Bonjour, je suis intéressé par votre annonce : " + annonce.titre);
            startActivity(intent);
        });

        emailBtn.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                    Uri.fromParts("mailto", vendeur.email, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                    "Annonce : " + annonce.titre);
            emailIntent.putExtra(Intent.EXTRA_TEXT,
                    "Bonjour, je suis intéressé par votre annonce.");
            startActivity(Intent.createChooser(emailIntent, "Envoyer un email..."));
        });

        editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AnnonceDetailActivity.this,
                    EditAnnonceActivity.class);
            intent.putExtra("annonce_id", annonce.id);
            startActivity(intent);
        });

        deleteBtn.setOnClickListener(v -> {
            boolean success = dbHelper.deleteAnnonce(annonce.id);
            if (success) {
                Toast.makeText(this,
                        "Annonce supprimée", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this,
                        "Erreur lors de la suppression",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int annonceId = getIntent().getIntExtra("annonce_id", -1);
        if (annonceId != -1) {
            annonce = dbHelper.getAnnonceById(annonceId);
            if (annonce != null) {
                vendeur = dbHelper.getVendeurById(annonce.vendeurId);
                updateUI();
            }
        }
    }

    private void updateUI() {
        titreTextView.setText(annonce.titre);
        prixTextView.setText(annonce.prix + " DH");
        categorieTextView.setText(annonce.categorie);
        descriptionTextView.setText(annonce.description);
        dateTextView.setText("Publié le : " + annonce.date);
        if (vendeur != null) {
            vendeurTextView.setText("Vendeur : " + vendeur.nom);
        }
    }
}
