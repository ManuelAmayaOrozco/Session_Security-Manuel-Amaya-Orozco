package com.es.seguridadsession.dto;

public class UsuarioDTO {

    private String nombre;
    private String password;
    private Boolean isAdmin;

    public UsuarioDTO(String nombre, String password, Boolean isAdmin) {
        this.nombre = nombre;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public UsuarioDTO(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() { return isAdmin; }

    public void setAdmin(Boolean admin) { isAdmin = admin; }
}
