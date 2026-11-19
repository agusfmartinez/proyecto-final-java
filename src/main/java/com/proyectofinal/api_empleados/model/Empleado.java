package com.proyectofinal.api_empleados.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int legajo;
    private String nombre;
    private String apellido;
    private String cuil;
    private String email;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    public Empleado() {
    }

    public Empleado(int legajo, String nombre, String apellido, String cuil) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuil = cuil;
        generarEmail();
    }

    public void generarEmail() {
        if (nombre == null || nombre.isBlank() || apellido == null || apellido.isBlank()) {
            this.email = null;
            return;
        }
        this.email = (nombre.charAt(0) + apellido + "@mail.com").toLowerCase();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        generarEmail();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
        generarEmail();
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public String getNombreCompleto() {
        return apellido + ", " + nombre;
    }

    @Override
    public String toString() {
        String nombreRol = (rol == null) ? "Sin rol" : rol.getNombre();
        return "Empleado [Legajo: " + legajo + " | " + getNombreCompleto()
                + " | CUIL: " + cuil + " | " + email + " | Rol: " + nombreRol + "]";
    }
}
