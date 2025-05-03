package com.example.gestionannonce;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionannonce.Adapter.AnnonceAdapter;
import com.example.gestionannonce.Database.DatabaseHelper;
import com.example.gestionannonce.Models.Annonce;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AccueilActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText searchInput;
    private DatabaseHelper dbHelper;
    private AnnonceAdapter adapter;
    private List<Annonce> annonces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // Initialisation des vues
        recyclerView = findViewById(R.id.recyclerViewAnnonces);
        searchInput = findViewById(R.id.searchInput);

        // Accès base de données
        dbHelper = new DatabaseHelper(this);
        annonces = dbHelper.getAllAnnonces();

        // Configuration de l'adapter avec le listener de clic
        adapter = new AnnonceAdapter(this, annonces, annonce -> {
            Intent intent = new Intent(AccueilActivity.this, AnnonceDetailActivity.class);
            intent.putExtra("annonce_id", annonce.id);
            startActivity(intent);
        });

        // Mise en place du RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Recherche en direct
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                List<Annonce> filteredList = dbHelper.searchAnnonces(keyword);
                adapter.updateList(filteredList);
            }

            @Override public void afterTextChanged(Editable s) {}


        });

        FloatingActionButton btnAddAnnonce = findViewById(R.id.btnAddAnnonce);
        btnAddAnnonce.setOnClickListener(v -> {
            Intent intent = new Intent(AccueilActivity.this, AjouterAnnonceActivity.class);
            startActivity(intent);
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        // Rafraîchir la liste depuis la base de données
        annonces = dbHelper.getAllAnnonces();
        adapter.updateList(annonces);
    }

}
