package com.example.codegym.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codegym.R;
import com.example.codegym.dto.UsuarioDTO;
import com.example.codegym.listeners.OnEditListener;
import com.example.codegym.listeners.OnDeleteListener;

import java.util.List;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UsersViewHolder> {

    private List<UsuarioDTO> usuarios;
    private OnEditListener<UsuarioDTO> editListener;
    private OnDeleteListener<UsuarioDTO> deleteListener;

    public UsuariosAdapter(List<UsuarioDTO> usuarios, OnEditListener editListener, OnDeleteListener deleteListener) {
        this.usuarios = usuarios;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_usuarios_item, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        UsuarioDTO usuario = usuarios.get(position);
        if (usuario != null) {
            holder.UsersNameTextView.setText(usuario.getNombre());
            holder.UsersEmailTextView.setText(usuario.getCorreo());
            holder.crudUsuariosEditButton.setOnClickListener(v -> {
                if (editListener != null) {
                    editListener.onEdit(usuario, position);
                }
            });
            holder.crudUsuariosDeleteButton.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDelete(usuario, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView UsersNameTextView,UsersEmailTextView;
        ImageButton crudUsuariosEditButton,crudUsuariosDeleteButton;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            UsersNameTextView = itemView.findViewById(R.id.UsersNameTextView);
            UsersEmailTextView = itemView.findViewById(R.id.UsersEmailTextView);
            crudUsuariosEditButton = itemView.findViewById(R.id.crudUsuariosEditButton);
            crudUsuariosDeleteButton = itemView.findViewById(R.id.crudUsuariosDeleteButton);
        }
    }
}
