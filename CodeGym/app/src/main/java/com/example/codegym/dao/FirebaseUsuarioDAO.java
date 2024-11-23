package com.example.codegym.dao;


import android.util.Log;

import com.example.codegym.listeners.OnItemReceivedListener;
import com.example.codegym.listeners.OnItemsReceivedListener;
import com.example.codegym.listeners.OnLoginListener;
import com.example.codegym.listeners.OnMailVerificationListener;
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
    public void crear(UsuarioDTO usuario, OnItemReceivedListener<Void> listener) {
        Log.d("FirebaseUsuarioDAO", "Iniciando creación de usuario: " + usuario.toString());
        db.collection("usuarios")
                .document()
                .set(usuario)
                .addOnSuccessListener(documentReference -> {
                    Log.d("FirebaseUsuarioDao", "Usuario creado");
                    listener.onItemReceived(null);
                })
                .addOnFailureListener(e -> {
                    Log.w("FirebaseUsuarioDao", "Error al crear el usuario", e);
                    listener.onError(e);
                });
    }

    @Override
    public void actualizar(UsuarioDTO usuario) {
        db.collection("usuarios")
                .document()
                .set(usuario, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("FirebaseUsuarioDao", "Usuario actualizado exitosamente"))
                .addOnFailureListener(e -> Log.w("FirebaseUsuarioDao", "Error al actualizar el usuario", e));
    }

    @Override
    public void eliminar(UsuarioDTO usuario) {
        db.collection("usuarios")
                .document()
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("FirebaseUsuarioDao", "Usuario eliminado exitosamente"))
                .addOnFailureListener(e -> Log.w("FirebaseUsuarioDao", "Error al eliminar el usuario", e));
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
                        Log.d("FirebaseUsuarioDao", "Usuario obtenido exitosamente con ID: " + id);
                    } else {
                        Log.w("FirebaseUsuarioDao", "No se encontró el usuario con ID: " + id);
                        listener.onItemReceived(null);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("FirebaseUsuarioDao", "Error al obtener el usuario", e);
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
                    Log.d("FirebaseUsuarioDao", "Usuarios obtenidos exitosamente");
                })
                .addOnFailureListener(e -> {
                    Log.w("FirebaseUsuarioDao", "Error al obtener los usuarios", e);
                    listener.onItemsReceived(new ArrayList<>()); // Retornamos una lista vacía en caso de error
                });
    }

    public void verificarMail(String mail, OnMailVerificationListener listener) {
        db.collection("usuarios")
                .whereEqualTo("correo", mail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && task.getResult().isEmpty()) {
                            listener.onMailAvailable();
                        } else {
                            listener.onMailAlreadyRegistered();
                        }
                    } else {
                        listener.onError(task.getException());
                    }
                });
    }

    public void iniciarSesion(String email, String password, OnLoginListener Listener) {
        db.collection("usuarios")
                .whereEqualTo("correo", email)
                .whereEqualTo("contrasenia", password)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        Listener.onLoginSuccess();
                    } else {
                        Listener.onLoginError(new Exception("Credenciales incorrectas"));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FirebaseUsuarioDAO", "Error al iniciar sesión: " + e.getMessage(), e);
                    Listener.onLoginError(e);
                });
    }
}