package com.proyectofinal.api_empleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectofinal.api_empleados.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
