package com.proyectofinal.api_empleados.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyectofinal.api_empleados.model.Rol;
import com.proyectofinal.api_empleados.repository.RolRepository;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }
}
