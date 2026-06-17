package com.tareaspring.demo.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioCreateDto {

    @Email(message = "El email debe tener un formato valido")
    @Size(min = 5, max = 50)
    @NotBlank
    private String email;

    @Size(min = 5, max = 20)
    @NotBlank
    private String nombre;

    @Size(min = 5, max = 20)
    @NotBlank
    private String apellido;

    @Pattern(regexp = "\\d{9}", message = "El telefono debe tener exactamente 9 digitos")
    private String telefono;

    private Long empresaId;

    @Size(min = 6, max = 30)
    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String role;

    // getters and setters
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
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}