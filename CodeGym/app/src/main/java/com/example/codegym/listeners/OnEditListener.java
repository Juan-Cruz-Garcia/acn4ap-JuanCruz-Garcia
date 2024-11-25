package com.example.codegym.listeners;

import com.example.codegym.dto.EjercicioDTO;

public interface OnEditListener {
    void onEdit(EjercicioDTO ejercicio, int position);
}
