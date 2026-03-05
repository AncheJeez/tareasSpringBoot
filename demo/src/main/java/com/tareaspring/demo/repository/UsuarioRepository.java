package com.tareaspring.demo.repository;

import com.tareaspring.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    interface UsuarioPorCiudadProjection {
        String getCiudad();
        Long getTotal();
    }

    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByEmpresaId(Long empresaId);

    @Query("""
            SELECT e.ciudad AS ciudad, COUNT(u) AS total
            FROM Usuario u
            JOIN u.empresa e
            GROUP BY e.ciudad
            ORDER BY COUNT(u) DESC
            """)
    List<UsuarioPorCiudadProjection> countUsuariosByCiudad();
}
