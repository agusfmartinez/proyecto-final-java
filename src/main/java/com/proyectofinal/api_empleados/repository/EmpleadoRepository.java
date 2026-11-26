package com.proyectofinal.api_empleados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyectofinal.api_empleados.dto.CantidadPorRol;
import com.proyectofinal.api_empleados.dto.EmpleadoConRol;
import com.proyectofinal.api_empleados.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    // WHERE LOWER(apellido) LIKE LOWER('%texto%')
    List<Empleado> findByApellidoContainingIgnoreCase(String texto);

    // JOIN con rol: WHERE rol.nombre = ?
    List<Empleado> findByRolNombre(String nombre);

    // ¿Ya existe un empleado con ese legajo / CUIL? Se usan al crear.
    boolean existsByLegajo(int legajo);

    boolean existsByCuil(String cuil);

    // Igual, pero ignorando al empleado con ese id: se usan al editar,
    // para que un empleado pueda conservar su propio legajo y CUIL.
    boolean existsByLegajoAndIdNot(int legajo, int id);

    boolean existsByCuilAndIdNot(String cuil, int id);

    @Query("SELECT new com.proyectofinal.api_empleados.dto.EmpleadoConRol("
            + "e.id, e.legajo, CONCAT(e.apellido, ', ', e.nombre), e.email, r.nombre) "
            + "FROM Empleado e JOIN e.rol r")
    List<EmpleadoConRol> listarConRol();

    @Query("SELECT new com.proyectofinal.api_empleados.dto.CantidadPorRol(r.nombre, COUNT(e)) "
            + "FROM Empleado e JOIN e.rol r GROUP BY r.nombre")
    List<CantidadPorRol> contarPorRol();
}
