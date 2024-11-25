package com.example.codegym;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codegym.adapter.AdminExerciseAdapter;
import com.example.codegym.dao.FirebaseEjercicioDAO;
import com.example.codegym.dto.EjercicioDTO;
import com.example.codegym.listeners.OnItemsReceivedListener;

import java.io.Serializable;
import java.util.List;

public class CrudEjercicosActivity extends AppCompatActivity {
    private TextView entrenadorTextView;
    private Button agregarEjercicioButton;
    private RecyclerView ejercicioRecyclerView;
    private AdminExerciseAdapter adminExerciseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_ejercicos);

        entrenadorTextView = findViewById(R.id.entrenadorTextView);
        agregarEjercicioButton = findViewById(R.id.agregarEjercicioButton);
        ejercicioRecyclerView = findViewById(R.id.ejercicioRecyclerView);
        ejercicioRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        agregarEjercicioButton.setOnClickListener(view -> {
            Intent intent = new Intent(CrudEjercicosActivity.this, AgregarEjercicioActivity.class);
            startActivity(intent);
            finish();
        });

        obtenerEjercicios();
    }

    private void obtenerEjercicios() {
        FirebaseEjercicioDAO ejercicioDAO = new FirebaseEjercicioDAO();
        ejercicioDAO.obtenerTodo(new OnItemsReceivedListener<EjercicioDTO>() {
            @Override
            public void onItemsReceived(List<EjercicioDTO> ejercicios) {
                if (ejercicios != null && !ejercicios.isEmpty()) {
                    adminExerciseAdapter = new AdminExerciseAdapter(
                            ejercicios,
                            (ejercicio, position) -> { // Lógica para eliminar
                                ejercicioDAO.eliminar((EjercicioDTO) ejercicio);
                                ejercicios.remove(position);
                                adminExerciseAdapter.notifyItemRemoved(position);
                                adminExerciseAdapter.notifyItemRangeChanged(position, ejercicios.size());
                            },
                            (ejercicio, position) -> { // Lógica para editar
                                Intent intent = new Intent(CrudEjercicosActivity.this, AgregarEjercicioActivity.class);
                                intent.putExtra("ejercicio",(EjercicioDTO) ejercicio); // Enviar el ejercicio para edición
                                startActivity(intent);
                            }
                    );
                    ejercicioRecyclerView.setAdapter(adminExerciseAdapter);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("CrudEjercicosActivity", "Error al obtener ejercicios", e);
            }
        });
    }
}