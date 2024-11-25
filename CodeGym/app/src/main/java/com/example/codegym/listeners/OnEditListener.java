package com.example.codegym.listeners;

import com.example.codegym.dto.EjercicioDTO;

public interface OnEditListener<T> {
    void onEdit(T item, int position);
}