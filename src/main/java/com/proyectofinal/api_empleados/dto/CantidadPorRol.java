package com.proyectofinal.api_empleados.dto;

// Resultado de un GROUP BY: cuántos empleados tiene cada rol.
public class CantidadPorRol {

    private String rol;
    private long cantidad;

    // COUNT() en JPQL devuelve Long, por eso cantidad es long.
    public CantidadPorRol(String rol, long cantidad) {
        this.rol = rol;
        this.cantidad = cantidad;
    }

    public String getRol() {
        return rol;
    }

    public long getCantidad() {
        return cantidad;
    }
}
