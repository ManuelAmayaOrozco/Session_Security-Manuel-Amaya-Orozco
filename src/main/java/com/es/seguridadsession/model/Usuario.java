package com.es.seguridadsession.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private String password;
    private Boolean isAdmin;

    public Usuario(Long id, String nombre, String password, Boolean isAdmin) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Usuario(String nombre, String password, Boolean isAdmin) {
        this.nombre = nombre;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
