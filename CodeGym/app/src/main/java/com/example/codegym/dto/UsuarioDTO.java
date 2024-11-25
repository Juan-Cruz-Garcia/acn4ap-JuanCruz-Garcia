package com.example.codegym.dto;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {
    private String correo;
    private String contrasenia;
    private boolean esAdmin;
    private String nombre;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String correo, String contrasenia, String nombre, boolean esAdmin) {

        this.correo = correo;
        this.contrasenia = contrasenia;
        this.esAdmin = esAdmin;
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String isEsAdmin() {
        if (esAdmin){
            return"administrador";
        }else {
            return "usuario";
        }
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                ", correo='" + correo + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", esAdmin=" + esAdmin +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}