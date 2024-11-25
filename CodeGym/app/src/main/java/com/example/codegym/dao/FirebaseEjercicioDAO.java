package com.example.codegym.dao;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.codegym.dto.UsuarioDTO;
import com.example.codegym.listeners.OnItemReceivedListener;
import com.example.codegym.listeners.OnItemsReceivedListener;
import com.example.codegym.dto.EjercicioDTO;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

public class FirebaseEjercicioDAO implements BaseDAO<EjercicioDTO> {
    private FirebaseFirestore db;

    public FirebaseEjercicioDAO() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void crear(EjercicioDTO ejercicio, OnItemReceivedListener<Void> listener) {
        db.collection("ejercicios")
                .document(ejercicio.getNombre())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Si el documento ya existe, notificar al listener con un mensaje descriptivo
                        if (listener != null) {
                            listener.onError(new Exception("El ejercicio ya existe."));
                        }
                    } else {
                        db.collection("ejercicios")
                                .document(ejercicio.getNombre())
                                .set(ejercicio)
                                .addOnSuccessListener(aVoid -> {
                                    if (listener != null) {
                                        listener.onItemReceived(null);
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    if (listener != null) {
                                        listener.onError(e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    if (listener != null) {
                        listener.onError(e);
                    }
                });
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
    public void eliminar(EjercicioDTO ejercicio) {
        db.collection("ejercicios")
                .whereEqualTo("nombre", ejercicio.getNombre()) // Filtrar por nombre del ejercicio
                .whereEqualTo("grupoMuscular", ejercicio.getGrupoMuscular()) // Filtrar por grupo muscular
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                db.collection("ejercicios")
                                        .document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(aVoid -> {
                                            System.out.println("Documento eliminado exitosamente: " + document.getId());
                                        })
                                        .addOnFailureListener(e -> {
                                            System.err.println("Error al eliminar documento: " + e.getMessage());
                                        });
                            }
                        } else {
                            System.out.println("No se encontraron documentos que coincidan con el contenido.");
                        }
                    } else {
                        System.err.println("Error al realizar la consulta: " + task.getException().getMessage());
                    }
                });
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