package com.example.codegym.dto;

import java.util.List;

public class EjercicioDTO {
    private String nombre;
    private List<String> grupoMuscular;
    private String tipo;

    // Constructor vacío requerido por Firestore
    public EjercicioDTO() {
    }

    public EjercicioDTO(String nombre, List<String> grupoMuscular, String tipo) {
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "EjercicioDTO{" +
                "nombre='" + nombre + '\'' +
                ", grupoMuscular=" + grupoMuscular +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}