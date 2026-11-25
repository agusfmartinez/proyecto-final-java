package com.proyectofinal.api_empleados.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.api_empleados.model.Rol;
import com.proyectofinal.api_empleados.service.RolService;

@RestController
@RequestMapping("/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public List<Rol> listarTodos() {
        return rolService.listarTodos();
    }
}
