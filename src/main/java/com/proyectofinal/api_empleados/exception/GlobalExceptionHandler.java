package com.proyectofinal.api_empleados.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Atrapa las excepciones que salen de cualquier controller
// y las convierte en una respuesta JSON con el código HTTP correcto.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Datos inválidos (lo lanza el Validador): 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> manejarDatosInvalidos(IllegalArgumentException e) {
        return armarRespuesta(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // El rol que mandó el cliente no existe: es un dato mal enviado, 400
    @ExceptionHandler(RolNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarRolNoEncontrado(RolNoEncontradoException e) {
        return armarRespuesta(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // El empleado pedido por ID no existe: 404
    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarEmpleadoNoEncontrado(EmpleadoNoEncontradoException e) {
        return armarRespuesta(HttpStatus.NOT_FOUND, e.getMessage());
    }

    // Legajo o CUIL repetido: 409
    @ExceptionHandler(EmpleadoDuplicadoException.class)
    public ResponseEntity<Map<String, String>> manejarEmpleadoDuplicado(EmpleadoDuplicadoException e) {
        return armarRespuesta(HttpStatus.CONFLICT, e.getMessage());
    }

    private ResponseEntity<Map<String, String>> armarRespuesta(HttpStatus estado, String mensaje) {
        return ResponseEntity.status(estado).body(Map.of("error", mensaje));
    }
}
