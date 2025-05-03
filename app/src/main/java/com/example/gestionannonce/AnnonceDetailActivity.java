package com.example.gestionannonce;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionannonce.Database.DatabaseHelper;
import com.example.gestionannonce.Models.Annonce;
import com.example.gestionannonce.Models.Vendeur;

import java.util.List;

public class AnnonceDetailActivity extends AppCompatActivity {

    private TextView titreTextView, prixTextView, categorieTextView, descriptionTextView, dateTextView, vendeurTextView;
    private Button appelBtn, smsBtn, emailBtn;
    private DatabaseHelper dbHelper;

    private Annonce annonce;
    private Vendeur vendeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_detail);

        dbHelper = new DatabaseHelper(this);

        titreTextView = findViewById(R.id.titreTextView);
        prixTextView = findViewById(R.id.prixTextView);
        categorieTextView = findViewById(R.id.categorieTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        dateTextView = findViewById(R.id.dateTextView);
        vendeurTextView = findViewById(R.id.vendeurTextView);
        appelBtn = findViewById(R.id.appelBtn);
        smsBtn = findViewById(R.id.smsBtn);
        emailBtn = findViewById(R.id.emailBtn);

        int annonceId = getIntent().getIntExtra("annonce_id", -1);
        if (annonceId == -1) {
            finish();
            return;
        }

        annonce = getAnnonceById(annonceId);
        if (annonce == null) {
            finish();
            return;
        }

        vendeur = getVendeurById(annonce.vendeurId);

        titreTextView.setText(annonce.titre);
        prixTextView.setText(annonce.prix + " DH");
        categorieTextView.setText("Catégorie : " + annonce.categorie);
        descriptionTextView.setText(annonce.description);
        dateTextView.setText("Publié le : " + annonce.date);

        if (vendeur != null) {
            vendeurTextView.setText("Vendeur : " + vendeur.nom);
        }

        appelBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + vendeur.telephone));
            startActivity(intent);
        });

        smsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + vendeur.telephone));
            intent.putExtra("sms_body", "Bonjour, je suis intéressé par votre annonce : " + annonce.titre);
            startActivity(intent);
        });

        emailBtn.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", vendeur.email, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Annonce : " + annonce.titre);
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Bonjour, je suis intéressé par votre annonce.");
            startActivity(Intent.createChooser(emailIntent, "Envoyer un email..."));
        });
    }

    private Annonce getAnnonceById(int id) {
        List<Annonce> list = dbHelper.getAllAnnonces();
        for (Annonce a : list) {
            if (a.id == id) return a;
        }
        return null;
    }

    private Vendeur getVendeurById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Vendeur WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            Vendeur v = new Vendeur();
            v.id = c.getInt(0);
            v.nom = c.getString(1);
            v.email = c.getString(2);
            v.telephone = c.getString(3);
            v.login = c.getString(4);
            v.motDePasse = c.getString(5);
            c.close();
            return v;
        }
        c.close();
        return null;
    }
}
