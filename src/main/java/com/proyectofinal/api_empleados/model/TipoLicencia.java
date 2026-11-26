package com.proyectofinal.api_empleados.model;

// Tipos de licencia permitidos. Un enum limita los valores posibles:
// si el JSON trae otro texto, Jackson no lo puede convertir y la API responde 400.
public enum TipoLicencia {
    VACACIONES,
    ENFERMEDAD,
    ESTUDIO
}
