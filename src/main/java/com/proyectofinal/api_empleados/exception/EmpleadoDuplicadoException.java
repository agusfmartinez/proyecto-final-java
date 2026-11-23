package com.proyectofinal.api_empleados.exception;

public class EmpleadoDuplicadoException extends RuntimeException {

    public EmpleadoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
