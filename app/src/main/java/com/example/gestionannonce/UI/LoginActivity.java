package com.example.gestionannonce.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionannonce.Database.DatabaseHelper;
import com.example.gestionannonce.Models.Vendeur;
import com.example.gestionannonce.R;

public class LoginActivity extends AppCompatActivity {

    private EditText inputLogin, inputPassword;
    private Button btnLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is already logged in
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE); // Use the same name across all activities
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            startActivity(new Intent(LoginActivity.this, AccueilActivity.class)); // Skip login and go to home screen
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        inputLogin = findViewById(R.id.inputLogin);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(v -> {
            String login = inputLogin.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            // Check the login credentials
            Vendeur vendeur = dbHelper.loginVendeur(login, password);
            if (vendeur != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putInt("userId", vendeur.id);
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, AccueilActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Login ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
