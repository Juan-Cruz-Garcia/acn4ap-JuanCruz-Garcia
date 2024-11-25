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

import com.example.codegym.dao.FirebaseEjercicioDAO;
import com.example.codegym.dao.FirebaseUsuarioDAO;
import com.example.codegym.dto.EjercicioDTO;
import com.example.codegym.dto.UsuarioDTO;
import com.example.codegym.listeners.OnItemReceivedListener;

import java.util.ArrayList;
import java.util.List;

public class ModificarUsuarioActivity extends AppCompatActivity {
    private TextView modificarTituloTextView;
    private EditText modificarNombreEditText, modificarEmailEditText;
    private Spinner modificarAdminSpinner;
    private Button modificarGuardarButton, modificarVolverButton;
    private FirebaseUsuarioDAO usuarioDAO;
    private UsuarioDTO usuarioExistente;
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modificarAdminSpinner.setAdapter(adapter);

        usuarioDAO = new FirebaseUsuarioDAO();

        Intent intent = getIntent();
        if (intent.hasExtra("usuario")) {
            usuarioExistente = (UsuarioDTO) intent.getSerializableExtra("usuario");
            if (usuarioExistente != null) {
                modificarNombreEditText.setText(usuarioExistente.getNombre());
                modificarEmailEditText.setText(usuarioExistente.getCorreo());
                modificarAdminSpinner.setSelection(adapter.getPosition(usuarioExistente.isEsAdmin()));
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


        if (usuarioExistente != null) {
            // Actualizar ejercicio existente
            usuarioExistente.setNombre(nombre);
            usuarioExistente.setCorreo(mail);
            if (modificarAdminSpinner.toString().equals("administrador")) {
                usuarioExistente.setEsAdmin(true);
            }else {
                usuarioExistente.setEsAdmin(false);
            }
            usuarioDAO.actualizar(usuarioExistente);
            Toast.makeText(ModificarUsuarioActivity.this, "Ejercicio Actualizado Exitosamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Crear nuevo ejercicio
            UsuarioDTO nuevoEjercicio = new UsuarioDTO();
            nuevoEjercicio.setNombre(nombre);
            nuevoEjercicio.setCorreo(mail);
            if (modificarAdminSpinner.toString().equals("administrador")) {
                nuevoEjercicio.setEsAdmin(true);
            }else {
                nuevoEjercicio.setEsAdmin(false);
            }

            usuarioDAO.crear(nuevoEjercicio, new OnItemReceivedListener<Void>() {
                @Override
                public void onItemReceived(Void aVoid) {
                    Log.d("AgregarEjercicioActivity", "Ejercicio guardado exitosamente");
                    Toast.makeText(ModificarUsuarioActivity.this, "Ejercicio Cargado Exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(Exception e) {
                    Log.w("AgregarEjercicioActivity", "Error al guardar el ejercicio: " + e.getMessage());
                    if (e.getMessage().equals("El ejercicio ya existe.")) {
                        Toast.makeText(ModificarUsuarioActivity.this, "El ejercicio ya existe.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ModificarUsuarioActivity.this, "Error al guardar el ejercicio: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
