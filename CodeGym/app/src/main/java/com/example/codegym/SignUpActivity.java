package com.example.codegym;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codegym.dao.FirebaseUsuarioDAO;
import com.example.codegym.dto.UsuarioDTO;
import com.example.codegym.helper.PasswordHasher;
import com.example.codegym.listeners.OnItemReceivedListener;
import com.example.codegym.listeners.OnMailVerificationListener;

public class SignUpActivity extends AppCompatActivity {

    private EditText registerNombre, registerEmail, registerPassword, registerConfirmPassword;
    private Button registerButton,registerloginButton;
    private FirebaseUsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        usuarioDAO = new FirebaseUsuarioDAO();
        registerNombre = findViewById(R.id.registerNombre);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfirmPassword = findViewById(R.id.registerConfirmPassword);
        registerButton = findViewById(R.id.registerButton);
        registerloginButton = findViewById(R.id.RegisterloginButton);

        registerloginButton.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
            startActivity(intent);
        });
        registerButton.setOnClickListener(View->{
            registrarUsuario();
        });

    }

    private void registrarUsuario() {
        String nombre = registerNombre.getText().toString().trim();
        String email = registerEmail.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();
        String confirmPassword = registerConfirmPassword.getText().toString().trim();


        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Log.d("SignUpActivity", "Fallo: Uno o más campos están vacíos.");
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Log.d("SignUpActivity", "Fallo: Las contraseñas no coinciden.");
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica si el correo ya está registrado
        verificarEmailDuplicado(email, () -> {
            // Si el correo está disponible, continua con el registro
            String hashedPassword = PasswordHasher.hashPassword(password);

            UsuarioDTO nuevoUsuario = new UsuarioDTO();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setCorreo(email);
            nuevoUsuario.setContrasenia(hashedPassword);
            nuevoUsuario.setEsAdmin(false);
            usuarioDAO.crear(nuevoUsuario, new OnItemReceivedListener<Void>() {
                @Override
                public void onItemReceived(Void item) {
                    runOnUiThread(() -> {
                    Log.d("SignUpActivity", "Usuario creado exitosamente.");
                    Toast.makeText(SignUpActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Log.d("SignUpActivity", "Redirigiendo a LogInActivity...");
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                    });
                }

                @Override
                public void onError(Exception e) {
                    Log.e("SignUpActivity", "Error al registrar usuario: "+ e.getMessage(),e);
                    runOnUiThread(() ->
                            Toast.makeText(SignUpActivity.this, "Error al registrar: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
                }
            });
        });
    }

    private void verificarEmailDuplicado(String email, Runnable onEmailAvailable) {
        usuarioDAO.verificarMail(email, new OnMailVerificationListener() {
            @Override
            public void onMailAvailable() {
                Log.d("SignUpActivity", "El correo está disponible para registro.");
                // Ejecuta la acción cuando el correo está disponible
                runOnUiThread(onEmailAvailable);
            }
            @Override
            public void onMailAlreadyRegistered() {
                // Notifica al usuario que el correo ya está registrado
                runOnUiThread(() ->
                        Toast.makeText(SignUpActivity.this, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
                );
            }
            @Override
            public void onError(Exception e) {
                // Notifica al usuario que hubo un error
                runOnUiThread(() ->
                        Toast.makeText(SignUpActivity.this, "Error al verificar el correo", Toast.LENGTH_SHORT).show()
                );
                Log.e("SignUpActivity", "Error al verificar correo", e);
            }
        });
    }

}
