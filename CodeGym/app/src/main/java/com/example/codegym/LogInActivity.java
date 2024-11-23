package com.example.codegym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.codegym.dao.FirebaseUsuarioDAO;
import com.example.codegym.helper.PasswordHasher;
import com.example.codegym.listeners.OnLoginListener;


public class LogInActivity extends AppCompatActivity {
    private FirebaseUsuarioDAO usuarioDAO;
    private Button loginButton, loginRegisterButton;
    private EditText loginEmail, loginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        usuarioDAO = new FirebaseUsuarioDAO();
        loginButton=findViewById(R.id.loginButton);
        loginRegisterButton=findViewById(R.id.loginRegisterButton);
        loginEmail=findViewById(R.id.loginEmail);
        loginPassword=findViewById(R.id.loginPassword);

        loginRegisterButton.setOnClickListener(View ->{
            Intent intent = new Intent(LogInActivity.this,SignUpActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(view -> {
            iniciarSesion();
        });

    }

    private void iniciarSesion() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();
        String hashedPassword = PasswordHasher.hashPassword(password);

        if (email.isEmpty() || password.isEmpty()) {
            Log.d("LogInActivity", "Fallo: Uno o más campos están vacíos.");
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        usuarioDAO.iniciarSesion(email, hashedPassword, new OnLoginListener() {
            @Override
            public void onLoginSuccess() {
                Log.d("LogInActivity", "Inicio de sesión exitoso.");
                Toast.makeText(LogInActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                // Redirigir a la actividad principal
                startActivity(new Intent(LogInActivity.this, WelcomeActivity.class));
                finish();
            }

            @Override
            public void onLoginError(Exception e) {
                Log.e("LogInActivity", "Error al iniciar sesión: " + e.getMessage(), e);
                Toast.makeText(LogInActivity.this, "Error al iniciar sesión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}