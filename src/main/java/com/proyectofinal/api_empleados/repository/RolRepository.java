package com.proyectofinal.api_empleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectofinal.api_empleados.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}
