package com.example.codegym.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codegym.R;
import com.example.codegym.dto.UsuarioDTO;
import com.example.codegym.listeners.OnEditListener;
import com.example.codegym.listeners.OnDeleteListener;

import java.util.List;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ExerciseViewHolder> {

    private List<UsuarioDTO> ejercicios;
    private OnEditListener<UsuarioDTO> editListener;
    private OnDeleteListener<UsuarioDTO> deleteListener;

    public UsuariosAdapter(List<UsuarioDTO> ejercicios, OnEditListener<UsuarioDTO> editListener, OnDeleteListener<UsuarioDTO> deleteListener) {
        this.ejercicios = ejercicios;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_usuarios_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        UsuarioDTO usuario = ejercicios.get(position);
        if (usuario != null) {
            holder.ExerciseNameTextView.setText(usuario.getNombre());
            holder.MuscleNameTextView.setText(usuario.getCorreo());

            holder.editButton.setOnClickListener(v -> {
                if (editListener != null) {
                    editListener.onEdit(usuario, position);
                }
            });

            holder.deleteButton.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDelete(usuario, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView ExerciseNameTextView,MuscleNameTextView;
        Button editButton,deleteButton;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            ExerciseNameTextView = itemView.findViewById(R.id.exerciseNameTextView);
            MuscleNameTextView = itemView.findViewById(R.id.muscleNameTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
