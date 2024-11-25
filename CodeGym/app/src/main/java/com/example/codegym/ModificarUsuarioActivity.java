package com.example.codegym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codegym.dao.FirebaseUsuarioDAO;
import com.example.codegym.dto.UsuarioDTO;
import com.example.codegym.listeners.OnItemReceivedListener;

public class ModificarUsuarioActivity extends AppCompatActivity {
    private TextView modificarTituloTextView;
    private EditText modificarNombreEditText, modificarEmailEditText;
    private Spinner modificarAdminSpinner;
    private Button modificarGuardarButton, modificarVolverButton;
    private FirebaseUsuarioDAO usuarioDAO;
    private UsuarioDTO usuarioExistente;
    private int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);

        modificarTituloTextView = findViewById(R.id.modificarTituloTextView);
        modificarNombreEditText = findViewById(R.id.modificarNombreEditText);
        modificarEmailEditText = findViewById(R.id.modificarEmailEditText);
        modificarAdminSpinner = findViewById(R.id.modificarAdminSpinner);
        modificarGuardarButton = findViewById(R.id.modificarGuardarButton);
        modificarVolverButton = findViewById(R.id.modificarVolverButton);
        String[] opciones = {"administrador", "usuario"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modificarAdminSpinner.setAdapter(adapter);

        usuarioDAO = new FirebaseUsuarioDAO();

        Intent intent = getIntent();
        if (intent.hasExtra("usuario")) {
            usuarioExistente = (UsuarioDTO) intent.getSerializableExtra("usuario");
            if (usuarioExistente != null) {
                if (usuarioExistente.isEsAdmin()) {
                    posicion = 0;
                } else {
                    posicion = 1;
                }
                modificarNombreEditText.setText(usuarioExistente.getNombre());
                modificarEmailEditText.setText(usuarioExistente.getCorreo());
                modificarAdminSpinner.setSelection(posicion);
                modificarGuardarButton.setText("Actualizar");
            }
        }
        modificarGuardarButton.setOnClickListener(view -> guardarUsuario());

        modificarVolverButton.setOnClickListener(view -> {
            Intent volverIntent = new Intent(ModificarUsuarioActivity.this, CrudUsuariosActivity.class);
            startActivity(volverIntent);
            finish();
        });
    }

    private void guardarUsuario() {
        String nombre = modificarNombreEditText.getText().toString().trim();
        String mail = modificarEmailEditText.getText().toString().trim();
        String spinner = modificarAdminSpinner.getSelectedItem().toString();

        if (nombre.isEmpty() || mail.isEmpty()) {
            Toast.makeText(ModificarUsuarioActivity.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean esAdmin = spinner.equals("administrador");

        if (usuarioExistente != null) {
            // Actualizar usuario existente
            usuarioExistente.setNombre(nombre);
            usuarioExistente.setCorreo(mail);
            usuarioExistente.setEsAdmin(esAdmin);
            usuarioDAO.actualizar(usuarioExistente);
            Toast.makeText(ModificarUsuarioActivity.this, "Usuario Actualizado Exitosamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Crear nuevo usuario
            UsuarioDTO nuevoUsuario = new UsuarioDTO();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setCorreo(mail);
            nuevoUsuario.setEsAdmin(esAdmin);

            usuarioDAO.crear(nuevoUsuario, new OnItemReceivedListener<Void>() {
                @Override
                public void onItemReceived(Void aVoid) {
                    Log.d("ModificarUsuarioActivity", "Usuario guardado exitosamente");
                    Toast.makeText(ModificarUsuarioActivity.this, "Usuario Cargado Exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(Exception e) {
                    Log.w("ModificarUsuarioActivity", "Error al guardar el usuario: " + e.getMessage());
                    if (e.getMessage().equals("El usuario ya existe.")) {
                        Toast.makeText(ModificarUsuarioActivity.this, "El usuario ya existe.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ModificarUsuarioActivity.this, "Error al guardar el usuario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}