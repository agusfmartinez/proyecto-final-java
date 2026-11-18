package com.proyectofinal.api_empleados.exception;

public class EmpleadoNoEncontradoException extends RuntimeException {

    public EmpleadoNoEncontradoException(int legajo) {
        super("No se encontró ningún empleado con legajo: " + legajo);
    }
}
