package com.example.itemmanagment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        // Login button click
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate username and password (you can add more validation)
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Perform login logic (e.g., check credentials against database)
                    // Display a toast message for now
                    showToast("Logged in successfully!");
                    navigateToDatabaseScreen();
                } else {
                    showToast("Please enter both username and password.");
                }
            }
        });

        // Create Account button click
        Button createAccountButton = findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate username and password (you can add more validation)
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Perform account creation logic (e.g., add to database)
                    // Display a toast message for now
                    showToast("Account created successfully!");
                    navigateToDatabaseScreen();
                } else {
                    showToast("Please enter both username and password.");
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToDatabaseScreen(){
        Intent intent = new Intent(MainActivity.this, Database.class);
        startActivity(intent);
    }
}