package com.proyectofinal.api_empleados.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Muchas licencias pertenecen a un empleado
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    // STRING: guarda el nombre ("VACACIONES"), no la posición (0, 1, 2)
    @Enumerated(EnumType.STRING)
    private TipoLicencia tipo;

    // fechaDesde -> columna fecha_desde (Spring pasa camelCase a snake_case)
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private String motivo;

    // Constructor vacío: lo necesita JPA
    public Licencia() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public TipoLicencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoLicencia tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
