package com.proyectofinal.api_empleados.exception;

public class RolNoEncontradoException extends RuntimeException {

    public RolNoEncontradoException(int id) {
        super("No se encontró ningún rol con ID: " + id);
    }
}
