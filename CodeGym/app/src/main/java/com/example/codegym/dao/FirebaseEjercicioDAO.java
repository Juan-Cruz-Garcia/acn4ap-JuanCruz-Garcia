package com.example.codegym.dao;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.codegym.dto.UsuarioDTO;
import com.example.codegym.listeners.OnItemReceivedListener;
import com.example.codegym.listeners.OnItemsReceivedListener;
import com.example.codegym.dto.EjercicioDTO;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

public class FirebaseEjercicioDAO implements BaseDAO<EjercicioDTO> {
    private FirebaseFirestore db;

    public FirebaseEjercicioDAO() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void crear(EjercicioDTO Ejercicio, OnItemReceivedListener<Void> listener) {
        db.collection("ejercicios")
                .document()
                .set(Ejercicio)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Ejercicio creado exitosamente"))
                .addOnFailureListener(e -> Log.w(TAG, "Error al crear el Ejercicio", e));
    }

    @Override
    public void actualizar(EjercicioDTO Ejercicio) {
        db.collection("ejercicios")
                .document()
                .set(Ejercicio, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Ejercicio actualizado exitosamente"))
                .addOnFailureListener(e -> Log.w(TAG, "Error al actualizar el Ejercicio", e));
    }

    @Override
    public void eliminar(EjercicioDTO Ejercicio) {
        db.collection("ejercicios")
                .document()
                .delete()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Ejercicio eliminado exitosamente"))
                .addOnFailureListener(e -> Log.w(TAG, "Error al eliminar el Ejercicio", e));
    }

    @Override
    public void obtener(String id, OnItemReceivedListener<EjercicioDTO> listener) {
        db.collection("ejercicios")
                .document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        EjercicioDTO Ejercicio = documentSnapshot.toObject(EjercicioDTO.class);
                        listener.onItemReceived(Ejercicio);
                        Log.d(TAG, "Ejercicio obtenido exitosamente ");
                    } else {
                        Log.w(TAG, "No se encontró el Ejercicio");
                        listener.onItemReceived(null);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error al obtener el Ejercicio", e);
                    listener.onItemReceived(null);
                });
    }

    @Override
    public void obtenerTodo(OnItemsReceivedListener<EjercicioDTO> listener) {
        db.collection("ejercicios")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<EjercicioDTO> ejercicios = queryDocumentSnapshots.toObjects(EjercicioDTO.class);
                    listener.onItemsReceived(ejercicios);
                    Log.d(TAG, "Ejercicios obtenidos exitosamente");
                    Log.d("FirebaseUsuarioDAO", "ejercicios recuperados: " + ejercicios.toString());

                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error al obtener los Ejercicios", e);
                    listener.onItemsReceived(new ArrayList<>()); // Retornamos una lista vacía en caso de error
                });
    }
}