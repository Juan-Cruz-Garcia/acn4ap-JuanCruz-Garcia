package com.example.codegym.dto;

import java.util.List;

public class EjercicioDTO {
    private String id;
    private String nombre;
    private List<String> grupoMuscular;

    // Constructor vacío requerido por Firestore
    public EjercicioDTO() {
    }

    public EjercicioDTO(String id, String nombre, List<String> grupoMuscular) {
        this.id = id;
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(List<String> grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    @Override
    public String toString() {
        return "EjercicioDTO{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", grupoMuscular=" + grupoMuscular +
                '}';
    }
}