package com.powerfit.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modelo que representa un Socio del gimnasio
 */
public class Socio {
    private int idSocio;
    private String nombreCompleto;
    private String dni;
    private String email;
    private String telefono;
    private int idPlan;
    private String estado;
    private LocalDateTime fechaRegistro;
    private LocalDate fechaVencimiento;

    // Constructores
    public Socio() {
    }

    public Socio(String nombreCompleto, String dni, String email, String telefono, int idPlan, LocalDate fechaVencimiento) {
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.idPlan = idPlan;
        this.estado = "activo";
        this.fechaVencimiento = fechaVencimiento;
    }

    public Socio(int idSocio, String nombreCompleto, String dni, String email, String telefono, 
                 int idPlan, String estado, LocalDate fechaVencimiento) {
        this.idSocio = idSocio;
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.idPlan = idPlan;
        this.estado = estado;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Getters y Setters
    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return "Socio{" +
                "idSocio=" + idSocio +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", idPlan=" + idPlan +
                ", estado='" + estado + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", fechaVencimiento=" + fechaVencimiento +
                '}';
    }
}
