package com.example.codegym.dao;

import com.example.codegym.dto.UsuarioDTO;
import com.example.codegym.listeners.OnItemReceivedListener;
import com.example.codegym.listeners.OnItemsReceivedListener;

public interface BaseDAO<T> {

    void crear(T item, OnItemReceivedListener<Void> listener);

    void actualizar(T item);

    void eliminar(T item);

    void obtener(String id, OnItemReceivedListener<T> listener);

    void obtenerTodo(OnItemsReceivedListener<T> listener);
}