package com.proyectofinal.api_empleados.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyectofinal.api_empleados.dto.CantidadPorRol;
import com.proyectofinal.api_empleados.dto.EmpleadoConRol;
import com.proyectofinal.api_empleados.exception.EmpleadoDuplicadoException;
import com.proyectofinal.api_empleados.exception.EmpleadoNoEncontradoException;
import com.proyectofinal.api_empleados.exception.RolNoEncontradoException;
import com.proyectofinal.api_empleados.model.Empleado;
import com.proyectofinal.api_empleados.model.Rol;
import com.proyectofinal.api_empleados.repository.EmpleadoRepository;
import com.proyectofinal.api_empleados.repository.RolRepository;
import com.proyectofinal.api_empleados.util.Validador;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final RolRepository rolRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository, RolRepository rolRepository) {
        this.empleadoRepository = empleadoRepository;
        this.rolRepository = rolRepository;
    }

    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    public Empleado obtenerPorId(int id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradoException(id));
    }

    public List<Empleado> buscarPorApellido(String apellido) {
        return empleadoRepository.findByApellidoContainingIgnoreCase(apellido);
    }

    public List<Empleado> listarPorRol(String rol) {
        return empleadoRepository.findByRolNombre(rol);
    }

    public List<EmpleadoConRol> listarConRol() {
        return empleadoRepository.listarConRol();
    }

    public List<CantidadPorRol> contarPorRol() {
        return empleadoRepository.contarPorRol();
    }

    public Empleado guardar(Empleado datos) {
        Validador.validarEmpleado(datos);

        // Regla de negocio: legajo y CUIL no se pueden repetir (409)
        if (empleadoRepository.existsByLegajo(datos.getLegajo())) {
            throw new EmpleadoDuplicadoException("Ya existe un empleado con el legajo " + datos.getLegajo());
        }
        if (empleadoRepository.existsByCuil(datos.getCuil())) {
            throw new EmpleadoDuplicadoException("Ya existe un empleado con el CUIL " + datos.getCuil());
        }

        Empleado nuevo = new Empleado(datos.getLegajo(), datos.getNombre(),
                datos.getApellido(), datos.getCuil());
        nuevo.setRol(obtenerRol(datos));

        return empleadoRepository.save(nuevo);
    }

    public Empleado actualizar(int id, Empleado datos) {
        Empleado existente = obtenerPorId(id);
        Validador.validarEmpleado(datos);

        // Pueden repetirse solo si son los del mismo empleado
        if (empleadoRepository.existsByLegajoAndIdNot(datos.getLegajo(), id)) {
            throw new EmpleadoDuplicadoException("Ya existe un empleado con el legajo " + datos.getLegajo());
        }
        if (empleadoRepository.existsByCuilAndIdNot(datos.getCuil(), id)) {
            throw new EmpleadoDuplicadoException("Ya existe un empleado con el CUIL " + datos.getCuil());
        }

        existente.setLegajo(datos.getLegajo());
        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setCuil(datos.getCuil());
        existente.setRol(obtenerRol(datos));

        return empleadoRepository.save(existente);
    }

    public void eliminar(int id) {
        if (!empleadoRepository.existsById(id)) {
            throw new EmpleadoNoEncontradoException(id);
        }
        empleadoRepository.deleteById(id);
    }

    // El JSON trae "rol": { "id": 2 }. Buscamos el Rol real en la base.
    private Rol obtenerRol(Empleado datos) {
        if (datos.getRol() == null || datos.getRol().getId() == null) {
            return null;
        }
        int rolId = datos.getRol().getId();
        return rolRepository.findById(rolId)
                .orElseThrow(() -> new RolNoEncontradoException(rolId));
    }
}
