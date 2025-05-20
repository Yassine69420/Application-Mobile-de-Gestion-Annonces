package com.example.gestionannonce.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionannonce.Adapter.AnnonceAdapter;
import com.example.gestionannonce.Database.DatabaseHelper;
import com.example.gestionannonce.Models.Annonce;
import com.example.gestionannonce.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AccueilActivity extends AppCompatActivity {

    private RecyclerView annoncesRecyclerView;
    private EditText searchInput;
    private DatabaseHelper dbHelper;
    private AnnonceAdapter annonceAdapter;
    private List<Annonce> annonces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // Initialisation des vues
        annoncesRecyclerView = findViewById(R.id.annoncesRecyclerView);
        searchInput = findViewById(R.id.searchInput);

        // Accès base de données
        dbHelper = new DatabaseHelper(this);
        annonces = dbHelper.getAllAnnonces();

        // RecyclerView Setup
        annoncesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        annonceAdapter = new AnnonceAdapter(this, annonces, annonce -> {
            Intent intent = new Intent(AccueilActivity.this, AnnonceDetailActivity.class);
            intent.putExtra("annonce_id", annonce.id);
            startActivity(intent);
        });
        annoncesRecyclerView.setAdapter(annonceAdapter);

        // Recherche en direct
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                List<Annonce> filteredList = dbHelper.searchAnnonces(keyword);
                annonceAdapter.updateList(filteredList);
            }

            @Override public void afterTextChanged(Editable s) {}
        });

        // Floating action button setup
        FloatingActionButton btnAddAnnonce = findViewById(R.id.btnAddAnnonce);
        btnAddAnnonce.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            if (isLoggedIn) {
                Intent intent = new Intent(AccueilActivity.this, AjouterAnnonceActivity.class);
                startActivity(intent);
            } else {

                Intent intent = new Intent(AccueilActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Logout button setup
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            // Clear session data
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);  // Set logged-in status to false
            editor.remove("userId");  // Optional: Remove the userId if you want to clear it
            editor.apply();  // Apply the changes

            // Redirect to MainActivity
            Intent logoutIntent = new Intent(AccueilActivity.this, MainActivity.class);
            startActivity(logoutIntent);
            finish();
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        annonces = dbHelper.getAllAnnonces();
        annonceAdapter.updateList(annonces);
    }
}
