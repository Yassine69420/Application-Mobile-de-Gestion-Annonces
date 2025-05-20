package com.example.gestionannonce.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionannonce.R;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Vérifier la session uniquement au lancement de l'activité
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE); // Utiliser le même nom que dans LoginActivity
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Si l'utilisateur est déjà connecté, passer à l'activité Accueil
            startActivity(new Intent(MainActivity.this, AccueilActivity.class));
            finish();
            return;
        }

        // Charger le layout de l'activité principale
        setContentView(R.layout.activity_main);

        // Initialiser les boutons
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Rediriger vers LoginActivity lorsqu'on clique sur "Connexion"
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Rediriger vers RegisterActivity lorsqu'on clique sur "Inscription"
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
