package com.example.codegym;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codegym.adapter.ExercisesAdapter;
import com.example.codegym.dao.FirebaseEjercicioDAO;
import com.example.codegym.dto.EjercicioDTO;
import com.example.codegym.listeners.OnItemsReceivedListener;

import java.util.List;

public class CrudEjercicosActivity extends AppCompatActivity {
    private TextView entrenadorTextView;
    private Spinner filtroMusculosSpinner;
    private Button agregarEjercicioButton;
    private RecyclerView ejercicioRecyclerView;
    private ExercisesAdapter exercisesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_ejercicos);

        entrenadorTextView = findViewById(R.id.entrenadorTextView);
        filtroMusculosSpinner = findViewById(R.id.filtroMusculosSpinner);
        agregarEjercicioButton = findViewById(R.id.agregarEjercicioButton);
        ejercicioRecyclerView = findViewById(R.id.ejercicioRecyclerView);
        ejercicioRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        obtenerEjercicios();
    }

    private void obtenerEjercicios() {
        FirebaseEjercicioDAO ejercicioDAO = new FirebaseEjercicioDAO();
        ejercicioDAO.obtenerTodo(new OnItemsReceivedListener<EjercicioDTO>() {
            @Override
            public void onItemsReceived(List<EjercicioDTO> ejercicios) {
                if (ejercicios != null && !ejercicios.isEmpty()) {
                    exercisesAdapter = new ExercisesAdapter(ejercicios);
                    ejercicioRecyclerView.setAdapter(exercisesAdapter);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("CrudEjercicosActivity", "Error al obtener ejercicios", e);
            }
        });
    }
}
