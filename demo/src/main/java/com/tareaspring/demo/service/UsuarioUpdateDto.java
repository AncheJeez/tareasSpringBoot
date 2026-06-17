package com.tareaspring.demo.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioUpdateDto {
    @NotBlank
    @Size(min = 5, max = 50)
    private String email;

    @Size(min = 5, max = 20)
    @NotBlank
    private String nombre;

    @Size(min = 5, max = 20)
    @NotBlank
    private String apellido;

    @Pattern(regexp = "\\d{9}")
    private String telefono;

    private Long empresaId;

    @NotBlank
    private String role;

    // getters/setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}