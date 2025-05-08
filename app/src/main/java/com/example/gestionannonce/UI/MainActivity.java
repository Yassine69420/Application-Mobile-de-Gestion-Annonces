package com.example.gestionannonce.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionannonce.R;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;

    // MainActivity.java
    // MainActivity.java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ Check session only on activity launch
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE); // Use the same name as in LoginActivity
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // If user is already logged in, go to AccueilActivity
            startActivity(new Intent(MainActivity.this, AccueilActivity.class));
            finish();
            return; // Exit onCreate() if user is logged in
        }

        setContentView(R.layout.activity_main);

        // Init buttons
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }


}
