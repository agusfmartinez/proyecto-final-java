package com.proyectofinal.api_empleados.exception;

public class EmpleadoNoEncontradoException extends RuntimeException {

    public EmpleadoNoEncontradoException(int id) {
        super("No se encontró ningún empleado con ID: " + id);
    }
}
