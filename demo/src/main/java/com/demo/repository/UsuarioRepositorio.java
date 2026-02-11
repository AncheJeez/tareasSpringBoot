package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.entidad.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
  Usuario findByNombre(String username);
}
