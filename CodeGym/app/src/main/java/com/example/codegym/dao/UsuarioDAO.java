package com.example.codegym.dao;

import com.example.codegym.dto.UsuarioDTO;
import com.example.codegym.singelton.FirestoreSingleton;

public class UsuarioDAO {
    public void crearUsuario(UsuarioDTO usuario) {
        FirestoreSingleton.getInstance().collection("usuarios").add(usuario);
        // ... manejar el resultado de la operación
    }

    // Otros métodos: obtenerUsuario, actualizarUsuario, eliminarUsuario, buscarUsuarios, etc.
}