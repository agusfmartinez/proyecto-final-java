package com.proyectofinal.api_empleados.util;

import com.proyectofinal.api_empleados.model.Empleado;

public class Validador {

    public static void validarEmpleado(Empleado empleado) {
        validarLegajo(empleado.getLegajo());
        validarNombre(empleado.getNombre());
        validarApellido(empleado.getApellido());
        validarCuil(empleado.getCuil());
    }

    public static void validarLegajo(int legajo) {
        if (legajo <= 0) {
            throw new IllegalArgumentException("El legajo debe ser mayor a cero.");
        }
    }

    public static void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
    }

    public static void validarApellido(String apellido) {
        if (apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
    }

    public static void validarCuil(String cuil) {
        if (cuil == null || !cuil.matches("\\d{11}")) {
            throw new IllegalArgumentException("El CUIL debe tener 11 dígitos numéricos.");
        }
    }
}
