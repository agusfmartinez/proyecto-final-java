package com.proyectofinal.api_empleados.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.api_empleados.dto.CantidadPorRol;
import com.proyectofinal.api_empleados.dto.EmpleadoConRol;
import com.proyectofinal.api_empleados.model.Empleado;
import com.proyectofinal.api_empleados.service.EmpleadoService;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<Empleado> listarTodos() {
        return empleadoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Empleado obtenerPorId(@PathVariable int id) {
        return empleadoService.obtenerPorId(id);
    }

    // GET /empleados?apellido=gom
    @GetMapping(params = "apellido")
    public List<Empleado> buscarPorApellido(@RequestParam String apellido) {
        return empleadoService.buscarPorApellido(apellido);
    }

    // GET /empleados/rol/Analista
    @GetMapping("/rol/{rol}")
    public List<Empleado> listarPorRol(@PathVariable String rol) {
        return empleadoService.listarPorRol(rol);
    }

    // GET /empleados/con-rol
    @GetMapping("/con-rol")
    public List<EmpleadoConRol> listarConRol() {
        return empleadoService.listarConRol();
    }

    // GET /empleados/cantidad-por-rol
    @GetMapping("/cantidad-por-rol")
    public List<CantidadPorRol> contarPorRol() {
        return empleadoService.contarPorRol();
    }

    @PostMapping
    public ResponseEntity<Empleado> crear(@RequestBody Empleado empleado) {
        Empleado creado = empleadoService.guardar(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable int id, @RequestBody Empleado empleado) {
        return empleadoService.actualizar(id, empleado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
