package com.example.codegym.dto;

import java.util.ArrayList;
import java.util.List;

public class RutinaDiaDTO {
    private List<EjercicioDTO> ejercicios;

    public RutinaDiaDTO() {
        this.ejercicios = new ArrayList<>(); // Inicializa la lista vacía
    }

    public List<EjercicioDTO> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<EjercicioDTO> ejercicios) {
        this.ejercicios = ejercicios;
    }
}
