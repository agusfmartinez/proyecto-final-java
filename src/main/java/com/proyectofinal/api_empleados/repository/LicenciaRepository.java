package com.proyectofinal.api_empleados.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectofinal.api_empleados.model.Licencia;

public interface LicenciaRepository extends JpaRepository<Licencia, Integer> {

    // Licencias de un empleado (empleado.id)
    List<Licencia> findByEmpleadoId(int empleadoId);

    // ¿El empleado tiene alguna licencia? (para no dejar borrarlo)
    boolean existsByEmpleadoId(int empleadoId);

    // ¿Hay otra licencia del empleado que se superpone con [desde, hasta]?
    // Dos rangos se superponen si uno empieza antes de que termine el otro
    // y termina después de que empiece el otro:
    //   existente.fechaDesde <= hasta  AND  existente.fechaHasta >= desde
    boolean existsByEmpleadoIdAndFechaDesdeLessThanEqualAndFechaHastaGreaterThanEqual(
            int empleadoId, LocalDate hasta, LocalDate desde);
}
