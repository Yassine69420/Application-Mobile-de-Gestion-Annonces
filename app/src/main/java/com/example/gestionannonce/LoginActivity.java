package com.example.gestionannonce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionannonce.Database.DatabaseHelper;
import com.example.gestionannonce.Models.Vendeur;

public class LoginActivity extends AppCompatActivity {

    private EditText inputLogin, inputPassword;
    private Button btnLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputLogin = findViewById(R.id.inputLogin);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(v -> {
            String login = inputLogin.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            Vendeur vendeur = dbHelper.loginVendeur(login, password);
            if (vendeur != null) {
                // Save the vendeur_id in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("vendeur_id", vendeur.id);  // Save vendeur id
                editor.apply();  // Apply changes to SharedPreferences

                // Navigate to AccueilActivity
                Intent intent = new Intent(LoginActivity.this, AccueilActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Login ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
