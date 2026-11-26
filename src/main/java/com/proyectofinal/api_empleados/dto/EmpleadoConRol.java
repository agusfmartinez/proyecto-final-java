package com.proyectofinal.api_empleados.dto;

// Vista resumida de un empleado: solo los datos que interesan mostrar,
// con el rol como texto plano en vez de un objeto anidado.
public class EmpleadoConRol {

    private int id;
    private int legajo;
    private String nombreCompleto;
    private String email;
    private String rol;

    public EmpleadoConRol(int id, int legajo, String nombreCompleto, String email, String rol) {
        this.id = id;
        this.legajo = legajo;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public int getLegajo() {
        return legajo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }
}
