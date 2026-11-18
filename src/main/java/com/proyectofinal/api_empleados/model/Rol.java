package com.proyectofinal.api_empleados.model;

public class Rol {

    private static int contadorId = 1;

    private int id;
    private String nombre;

    public Rol() {
        this.id = contadorId++;
    }

    public Rol(String nombre) {
        this.id = contadorId++;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Rol [ID: " + id + " | " + nombre + "]";
    }
}
