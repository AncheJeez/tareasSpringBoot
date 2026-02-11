package com.demo.entidad;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.demo.entidad.enumerado.Rol;

@Entity
public class Usuario implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String nombre;

  @Column(nullable = false)
  private String contrasena; // almacenada en BBDD como HASH (BCrypt)

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Rol rol;

  public Usuario() {}

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }

  public String getContrasena() { return contrasena; }
  public void setContrasena(String contrasena) { this.contrasena = contrasena; }

  public Rol getRol() { return rol; }
  public void setRol(Rol rol) { this.rol = rol; }

  // Métodos de UserDetails (interface implementada)
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
	// Convierte el rol asignado en autoridad con ROLE_
	return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + rol.name()));
  }

  @Override
  public @Nullable String getPassword() {
	return contrasena;
  }

  @Override
  public String getUsername() {
	return nombre;
  }
  
  /* Sugerencias a añadir, lo dejo comentado de momento
  @Override
  public boolean isAccountNonExpired() {
    return true; // Puedes agregar lógica de expiración si lo necesitas
  }

  @Override
  public boolean isAccountNonLocked() {
    return true; // Puedes agregar lógica de bloqueo si lo necesitas
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true; // Puedes agregar lógica de expiración de credenciales si lo necesitas
  }

  @Override
  public boolean isEnabled() {
    return true; // Puedes agregar un campo 'activo' en la entidad si lo necesitas
  }
  */
}