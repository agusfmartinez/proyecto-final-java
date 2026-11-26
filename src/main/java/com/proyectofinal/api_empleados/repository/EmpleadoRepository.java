package com.proyectofinal.api_empleados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectofinal.api_empleados.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    // WHERE LOWER(apellido) LIKE LOWER('%texto%')
    List<Empleado> findByApellidoContainingIgnoreCase(String texto);

    // JOIN con rol: WHERE rol.nombre = ?
    List<Empleado> findByRolNombre(String nombre);
}
