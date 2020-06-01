package com.recordwatch.recordwatch.pojos;

public class Usuario {
    private String contrasena;

    public Usuario() {
    }

    public Usuario(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "contrasena='" + contrasena + '\'' +
                '}';
    }
}
