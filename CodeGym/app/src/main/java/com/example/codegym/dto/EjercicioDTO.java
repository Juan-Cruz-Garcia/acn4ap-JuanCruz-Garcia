package com.example.codegym.dto;

import java.util.List;

public class EjercicioDTO {
    private String nombre;
    private List<String> grupoMuscular;

    // Constructor vacío requerido por Firestore
    public EjercicioDTO() {
    }

    public EjercicioDTO(String id, String nombre, List<String> grupoMuscular) {
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular;
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
                ", nombre='" + nombre + '\'' +
                ", grupoMuscular=" + grupoMuscular +
                '}';
    }
}