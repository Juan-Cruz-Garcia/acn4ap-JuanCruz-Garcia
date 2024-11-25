package com.example.codegym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.codegym.adapter.UsuariosAdapter;
import com.example.codegym.dao.FirebaseUsuarioDAO;
import com.example.codegym.dto.UsuarioDTO;
import com.example.codegym.listeners.OnItemsReceivedListener;

import java.util.List;

public class CrudUsuariosActivity extends AppCompatActivity {
private TextView entrenadorTextView;
private RecyclerView usuariosRecyclerView;
private UsuariosAdapter usuariosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_usuarios);

        entrenadorTextView = findViewById(R.id.entrenadorTextView);
        usuariosRecyclerView = findViewById(R.id.usuariosRecyclerView);
        usuariosRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        obtenerUsuarios();
    }

    private void obtenerUsuarios() {
        FirebaseUsuarioDAO usuarioDAO = new FirebaseUsuarioDAO();

        usuarioDAO.obtenerTodo(new OnItemsReceivedListener<UsuarioDTO>() {
            @Override
            public void onItemsReceived(List<UsuarioDTO> usuarios) {
                if (usuarios != null && !usuarios.isEmpty()) {
                    usuariosAdapter = new UsuariosAdapter(
                            usuarios,
                            (usuario, position) -> { // Lógica para eliminar
                                usuarioDAO.eliminar((UsuarioDTO) usuario);
                                usuarios.remove(position);
                                usuariosAdapter.notifyItemRemoved(position);
                                usuariosAdapter.notifyItemRangeChanged(position, usuarios.size());
                            },
                            (usuario, position) -> { // Lógica para editar
                                Intent intent = new Intent(CrudUsuariosActivity.this, ModificarUsuarioActivity.class);
                                intent.putExtra("usuario",(UsuarioDTO) usuario); // Enviar el ejercicio para edición
                                startActivity(intent);
                            }
                    );
                    usuariosRecyclerView.setAdapter(usuariosAdapter);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("CrudUsuariosActivity", "Error al obtener Usuarios", e);

            }

        });
    }
}