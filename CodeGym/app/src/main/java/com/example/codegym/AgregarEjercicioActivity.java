package com.example.codegym;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codegym.dao.FirebaseEjercicioDAO;
import com.example.codegym.dto.EjercicioDTO;
import com.example.codegym.listeners.OnItemReceivedListener;

import java.util.ArrayList;
import java.util.List;

public class AgregarEjercicioActivity extends AppCompatActivity {
    private TextView agregarTituloTextView;
    private EditText agregarNombreEditText, agregarMusculosEditText;
    private Button agregarGuardarButton, agregarVolverButton;
    private Spinner agregarTipoSpinner;
    private FirebaseEjercicioDAO ejercicioDAO;
    private EjercicioDTO ejercicioExistente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ejercicio);

        agregarTituloTextView = findViewById(R.id.agregarTituloTextView);
        agregarNombreEditText = findViewById(R.id.agregarNombreEditText);
        agregarMusculosEditText = findViewById(R.id.agregarMusculosEditText);
        agregarTipoSpinner = findViewById(R.id.agregarTipoSpinner);
        agregarGuardarButton = findViewById(R.id.agregarGuardarButton);
        agregarVolverButton = findViewById(R.id.agregarVolverButton);
        agregarTipoSpinner = findViewById(R.id.agregarTipoSpinner);
        String[] opciones = {"Peso", "Tiempo", "Repetición"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agregarTipoSpinner.setAdapter(adapter);

        ejercicioDAO = new FirebaseEjercicioDAO();

        // Verificar si se está recibiendo un ejercicio para editar
        Intent intent = getIntent();
        if (intent.hasExtra("ejercicio")) {
            ejercicioExistente = (EjercicioDTO) intent.getSerializableExtra("ejercicio");
            if (ejercicioExistente != null) {
                agregarNombreEditText.setText(ejercicioExistente.getNombre());
                agregarMusculosEditText.setText(String.join(", ", ejercicioExistente.getGrupoMuscular()));
                agregarTipoSpinner.setSelection(adapter.getPosition(ejercicioExistente.getTipo()));
                agregarGuardarButton.setText("Actualizar");
            }
        }

        agregarGuardarButton.setOnClickListener(view -> guardarEjercicio());

        agregarVolverButton.setOnClickListener(view -> {
            Intent volverIntent = new Intent(AgregarEjercicioActivity.this, CrudEjercicosActivity.class);
            startActivity(volverIntent);
            finish();
        });
    }

    private void guardarEjercicio() {
        String nombre = agregarNombreEditText.getText().toString().trim();
        String musculosInput = agregarMusculosEditText.getText().toString().trim();
        String tipo = agregarTipoSpinner.getSelectedItem().toString();

        if (nombre.isEmpty() || musculosInput.isEmpty()) {
            Toast.makeText(AgregarEjercicioActivity.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] musculosArray = musculosInput.split("[, .]+");
        List<String> musculosList = new ArrayList<>();
        for (String musculo : musculosArray) {
            musculosList.add(musculo.trim());
        }

        if (ejercicioExistente != null) {
            // Actualizar ejercicio existente
            ejercicioExistente.setNombre(nombre);
            ejercicioExistente.setGrupoMuscular(musculosList);
            ejercicioExistente.setTipo(tipo);

            ejercicioDAO.actualizar(ejercicioExistente);
            Toast.makeText(AgregarEjercicioActivity.this, "Ejercicio Actualizado Exitosamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Crear nuevo ejercicio
            EjercicioDTO nuevoEjercicio = new EjercicioDTO();
            nuevoEjercicio.setNombre(nombre);
            nuevoEjercicio.setGrupoMuscular(musculosList);
            nuevoEjercicio.setTipo(tipo);

            ejercicioDAO.crear(nuevoEjercicio, new OnItemReceivedListener<Void>() {
                @Override
                public void onItemReceived(Void aVoid) {
                    Log.d("AgregarEjercicioActivity", "Ejercicio guardado exitosamente");
                    Toast.makeText(AgregarEjercicioActivity.this, "Ejercicio Cargado Exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(Exception e) {
                    Log.w("AgregarEjercicioActivity", "Error al guardar el ejercicio: " + e.getMessage());
                    if (e.getMessage().equals("El ejercicio ya existe.")) {
                        Toast.makeText(AgregarEjercicioActivity.this, "El ejercicio ya existe.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AgregarEjercicioActivity.this, "Error al guardar el ejercicio: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
