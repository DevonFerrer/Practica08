package com.powerfit.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Modelo que representa un Plan de entrenamiento
 */
public class Plan {
    private int idPlan;
    private String nombrePlan;
    private String descripcion;
    private BigDecimal precio;
    private int duracionMeses;
    private LocalDateTime fechaCreacion;

    // Constructores
    public Plan() {
    }

    public Plan(int idPlan, String nombrePlan, String descripcion, BigDecimal precio, int duracionMeses) {
        this.idPlan = idPlan;
        this.nombrePlan = nombrePlan;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMeses = duracionMeses;
    }

    public Plan(String nombrePlan, String descripcion, BigDecimal precio, int duracionMeses) {
        this.nombrePlan = nombrePlan;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMeses = duracionMeses;
    }

    // Getters y Setters
    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "idPlan=" + idPlan +
                ", nombrePlan='" + nombrePlan + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", duracionMeses=" + duracionMeses +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
