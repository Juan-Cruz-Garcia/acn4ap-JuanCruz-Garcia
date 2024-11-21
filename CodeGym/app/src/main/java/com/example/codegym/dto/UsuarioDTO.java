package com.example.codegym.dto;

public class UsuarioDTO {
    private String correo;
    private String contrasenia;
    private boolean esAdmin;
    private String nombre;

    public UsuarioDTO() {}

    public UsuarioDTO(String correo, String contraseña, boolean esAdmin, String nombre) {
        this.correo = correo;
        this.contrasenia = contraseña;
        this.esAdmin = esAdmin;
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contrasenia;
    }

    public void setContraseña(String contraseña) {
        this.contrasenia = contraseña;
    }

    public boolean isEsAdmin() {
        return esAdmin;
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
                "correo='" + correo + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", esAdmin=" + esAdmin +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
