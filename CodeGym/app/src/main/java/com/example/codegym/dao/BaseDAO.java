package com.example.codegym.dao;

import com.example.codegym.dao.listeners.OnItemReceivedListener;
import com.example.codegym.dao.listeners.OnItemsReceivedListener;

public interface BaseDAO<T> {
    void crear(T item);

    void actualizar(T item);

    void eliminar(T item);

    void obtener(String id, OnItemReceivedListener<T> listener);

    void obtenerTodo(OnItemsReceivedListener<T> listener);
}