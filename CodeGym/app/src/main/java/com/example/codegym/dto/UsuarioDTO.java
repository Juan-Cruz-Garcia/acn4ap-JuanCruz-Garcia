package com.example.codegym.dto;

public class UsuarioDTO {
    private String id;
    private String correo;
    private String contrasenia;
    private boolean esAdmin;
    private String nombre;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String id, String correo, String contrasenia, String nombre, boolean esAdmin) {
        this.id = id;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.esAdmin = esAdmin;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", esAdmin=" + esAdmin +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}