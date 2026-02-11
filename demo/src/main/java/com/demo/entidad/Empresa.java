package com.demo.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El NIF no puede estar vacío")
    private String nif;

    @NotBlank(message = "La dirección social no puede estar vacía")
    private String direccionSocial;

    @NotBlank(message = "La razón social no puede estar vacía")
    private String razonSocial;

    @Positive(message = "El capital social debe ser positivo")
    private Double capitalSocial;

    @NotNull(message = "La fecha de constitución es obligatoria")
    private LocalDate fechaConstitucion;

    public Empresa() {}

    public Empresa(Integer id, String nombre, String nif, String direccionSocial,
        String razonSocial, Double capitalSocial, LocalDate fechaConstitucion) {
        this.id = id;
        this.nombre = nombre;
        this.nif = nif;
        this.direccionSocial = direccionSocial;
        this.razonSocial = razonSocial;
        this.capitalSocial = capitalSocial;
        this.fechaConstitucion = fechaConstitucion;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getDireccionSocial() {
        return direccionSocial;
    }

    public void setDireccionSocial(String direccionSocial) {
        this.direccionSocial = direccionSocial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Double getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(Double capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public LocalDate getFechaConstitucion() {
        return fechaConstitucion;
    }

    public void setFechaConstitucion(LocalDate fechaConstitucion) {
        this.fechaConstitucion = fechaConstitucion;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nif='" + nif + '\'' +
                ", direccionSocial='" + direccionSocial + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", capitalSocial=" + capitalSocial +
                ", fechaConstitucion=" + fechaConstitucion +
                '}';
    }
}