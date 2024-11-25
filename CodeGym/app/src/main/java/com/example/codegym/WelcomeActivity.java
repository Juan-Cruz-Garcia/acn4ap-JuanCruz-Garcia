package com.example.codegym;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codegym.adapter.ExercisesAdapter;
import com.example.codegym.dao.FirebaseEjercicioDAO;
import com.example.codegym.dto.EjercicioDTO;
import com.example.codegym.listeners.OnItemsReceivedListener;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private RecyclerView exercisesRecyclerView;
    private ExercisesAdapter exercisesAdapter;
    // Variables para el cronómetro
    private TextView timerTextView;
    private Button startButton, stopButton;
    private ImageButton resetButton;
    private Handler timerHandler = new Handler();
    private boolean isRunning = false;
    private int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView welcomeTextView = findViewById(R.id.bienvenidaTextView);
        welcomeTextView.setText("Bienvenido, [Nombre Usuario]");

        exercisesRecyclerView = findViewById(R.id.ejerciciosRecyclerView);
        exercisesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configura el cronómetro
        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);

        configurarCronometro();
        obtenerEjercicios();
    }

    private void configurarCronometro() {
        startButton.setOnClickListener(view -> {
            if (!isRunning) {
                isRunning = true;
                iniciarCronometro();
            }
        });

        stopButton.setOnClickListener(view -> isRunning = false);

        resetButton.setOnClickListener(view -> {
            isRunning = false;
            seconds = 0;
            actualizarTextoCronometro();
        });
    }

    private void iniciarCronometro() {
        timerHandler.post(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    seconds++;
                    actualizarTextoCronometro();
                    timerHandler.postDelayed(this, 1000); // Actualiza cada segundo
                }
            }
        });
    }

    private void actualizarTextoCronometro() {
        int horas = seconds / 3600;
        int minutos = (seconds % 3600) / 60;
        int segundos = seconds % 60;
        String tiempo = String.format("%02d:%02d:%02d", horas, minutos, segundos);
        timerTextView.setText(tiempo);
    }

    private void obtenerEjercicios() {
        FirebaseEjercicioDAO ejercicioDAO = new FirebaseEjercicioDAO();
        ejercicioDAO.obtenerTodo(new OnItemsReceivedListener<EjercicioDTO>() {
            @Override
            public void onItemsReceived(List<EjercicioDTO> ejercicios) {
                if (ejercicios != null && !ejercicios.isEmpty()) {
                    exercisesAdapter = new ExercisesAdapter(ejercicios);
                    exercisesRecyclerView.setAdapter(exercisesAdapter);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("WelcomeActivity", "Error al obtener ejercicios", e);
            }
        });
    }
}
