package com.example.codegym.dao;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.codegym.dao.listeners.OnItemReceivedListener;
import com.example.codegym.dao.listeners.OnItemsReceivedListener;
import com.example.codegym.dto.UsuarioDTO;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUsuarioDAO implements BaseDAO<UsuarioDTO> {
    private FirebaseFirestore db;

    public FirebaseUsuarioDAO() {
        db = FirebaseFirestore.getInstance();
    }
    @Override
    public void crear(UsuarioDTO usuario) {
        db.collection("usuarios")
                .document(usuario.getId())
                .set(usuario)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Usuario creado exitosamente con ID: " + usuario.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error al crear el usuario", e));
    }

    @Override
    public void actualizar(UsuarioDTO usuario) {
        db.collection("usuarios")
                .document(usuario.getId())
                .set(usuario, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Usuario actualizado exitosamente con ID: " + usuario.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error al actualizar el usuario", e));
    }

    @Override
    public void eliminar(UsuarioDTO usuario) {
        db.collection("usuarios")
                .document(usuario.getId())
                .delete()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Usuario eliminado exitosamente con ID: " + usuario.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error al eliminar el usuario", e));
    }

    @Override
    public void obtener(String id, OnItemReceivedListener<UsuarioDTO> listener) {
        db.collection("usuarios")
                .document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        UsuarioDTO usuario = documentSnapshot.toObject(UsuarioDTO.class);
                        listener.onItemReceived(usuario);
                        Log.d(TAG, "Usuario obtenido exitosamente con ID: " + id);
                    } else {
                        Log.w(TAG, "No se encontró el usuario con ID: " + id);
                        listener.onItemReceived(null);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error al obtener el usuario", e);
                    listener.onItemReceived(null);
                });
    }

    @Override
    public void obtenerTodo(OnItemsReceivedListener<UsuarioDTO> listener) {
        db.collection("usuarios")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<UsuarioDTO> usuarios = queryDocumentSnapshots.toObjects(UsuarioDTO.class);
                    listener.onItemsReceived(usuarios);
                    Log.d(TAG, "Usuarios obtenidos exitosamente");
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error al obtener los usuarios", e);
                    listener.onItemsReceived(new ArrayList<>()); // Retornamos una lista vacía en caso de error
                });
    }
}