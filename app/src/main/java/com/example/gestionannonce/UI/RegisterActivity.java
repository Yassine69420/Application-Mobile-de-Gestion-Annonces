package com.example.gestionannonce.UI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionannonce.Database.DatabaseHelper;
import com.example.gestionannonce.Models.Vendeur;
import com.example.gestionannonce.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputNom, inputEmail, inputTelephone, inputLogin, inputPassword;
    private Button btnRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialiser les vues
        inputNom = findViewById(R.id.inputNom);
        inputEmail = findViewById(R.id.inputEmail);
        inputTelephone = findViewById(R.id.inputTelephone);
        inputLogin = findViewById(R.id.inputLogin);
        inputPassword = findViewById(R.id.inputPassword);
        btnRegister = findViewById(R.id.btnRegister);

        dbHelper = new DatabaseHelper(this);

        btnRegister.setOnClickListener(v -> {
            String nom = inputNom.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String telephone = inputTelephone.getText().toString().trim();
            String login = inputLogin.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            // Vérification des champs
            if (nom.isEmpty() || email.isEmpty() || telephone.isEmpty() || login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Créer un objet Vendeur
            Vendeur vendeur = new Vendeur(nom, email, telephone, login, password);

            // Insérer le vendeur dans la base de données
            boolean registered = dbHelper.registerVendeur(vendeur);
            if (registered) {
                Toast.makeText(this, "Compte créé avec succès", Toast.LENGTH_SHORT).show();
                finish(); // Retour à la page de login
            } else {
                Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
