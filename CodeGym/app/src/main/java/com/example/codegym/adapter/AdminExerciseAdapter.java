package com.example.codegym.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codegym.R;
import com.example.codegym.dto.EjercicioDTO;
import com.example.codegym.listeners.onDeleteListener;
import com.example.codegym.listeners.OnEditListener;

import java.util.List;

public class AdminExerciseAdapter extends RecyclerView.Adapter<AdminExerciseAdapter.ExerciseViewHolder> {
    private List<EjercicioDTO> ejercicios;
    private onDeleteListener deleteListener;
    private OnEditListener editListener;

    // Constructor para inicializar los datos y los listeners
    public AdminExerciseAdapter(List<EjercicioDTO> ejercicios, onDeleteListener deleteListener, OnEditListener editListener) {
        this.ejercicios = ejercicios;
        this.deleteListener = deleteListener; // Asignación del listener de eliminación
        this.editListener = editListener; // Asignación del listener de edición
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_crud_item, parent, false);
        return new ExerciseViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        EjercicioDTO ejercicio = ejercicios.get(position);
        if (ejercicio != null) {
            holder.ExerciseNameTextView.setText(ejercicio.getNombre());

            // Conversión de la lista `grupoMuscular` a cadena
            List<String> grupoMuscular = ejercicio.getGrupoMuscular();
            if (grupoMuscular != null && !grupoMuscular.isEmpty()) {
                String grupos = String.join(", ", grupoMuscular);
                holder.MuscleNameTextView.setText(grupos);
            } else {
                holder.MuscleNameTextView.setText("Sin grupo muscular asignado");
            }
            // Configuración de botones
            holder.deleteButton.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDelete(ejercicio, position);
                }
            });
            holder.editButton.setOnClickListener(v -> {
                if (editListener != null) {
                    editListener.onEdit(ejercicio, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    // Clase interna para manejar las vistas
    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private TextView ExerciseNameTextView, MuscleNameTextView;
        private ImageButton editButton, deleteButton;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            ExerciseNameTextView = itemView.findViewById(R.id.ExerciseNameTextView);
            MuscleNameTextView = itemView.findViewById(R.id.MuscleNameTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
