package com.es.seguridadsession.dto;

public class UsuarioInsertDTO {

    private String nombre;
    private String password1;
    private String password2;
    private Boolean isAdmin;

    public UsuarioInsertDTO(String nombre, String password1, String password2, Boolean isAdmin) {
        this.nombre = nombre;
        this.password1 = password1;
        this.password2 = password2;
        this.isAdmin = isAdmin;
    }

    public UsuarioInsertDTO(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public Boolean getAdmin() { return isAdmin; }

    public void setAdmin(Boolean admin) { isAdmin = admin; }
}
