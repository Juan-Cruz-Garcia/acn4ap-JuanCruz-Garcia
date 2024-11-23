package com.example.codegym;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.codegym.dao.FirebaseEjercicioDAO;
import com.example.codegym.dao.listeners.OnItemsReceivedListener;
import com.example.codegym.dto.EjercicioDTO;

import java.util.Arrays;
import java.util.List;


public class LogInActivity extends AppCompatActivity {
    private static final String TAG = "logInActivity";
    private FirebaseEjercicioDAO ejercicioDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ejercicioDAO = new FirebaseEjercicioDAO();

        Button cargarEjercicioButton = findViewById(R.id.cargarEjercicioButton);
        Button mostrarEjerciciosButton = findViewById(R.id.mostrarEjerciciosButton);

        cargarEjercicioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarEjercicio();
            }
        });

        mostrarEjerciciosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarEjercicios();
            }
        });
    }

    private void cargarEjercicio() {
        EjercicioDTO nuevoEjercicio = new EjercicioDTO("1", "Flexiones", Arrays.asList("Pecho", "Tríceps"));
        ejercicioDAO.crear(nuevoEjercicio);
        Toast.makeText(this, "Ejercicio cargado", Toast.LENGTH_SHORT).show();
    }

    private void mostrarEjercicios() {
        ejercicioDAO.obtenerTodo(new OnItemsReceivedListener<EjercicioDTO>() {
            @Override
            public void onItemsReceived(List<EjercicioDTO> ejercicios) {
                if (ejercicios.isEmpty()) {
                    Toast.makeText(LogInActivity.this, "No hay ejercicios disponibles", Toast.LENGTH_SHORT).show();
                } else {
                    for (EjercicioDTO ejercicio : ejercicios) {
                        Log.d("Ejercicio", ejercicio.toString());
                    }
                    Toast.makeText(LogInActivity.this, "Ejercicios mostrados en log", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Error al obtener los ejercicios", e);
                Toast.makeText(LogInActivity.this, "Error al obtener ejercicios", Toast.LENGTH_SHORT).show();
            }
        });
    }


}