package com.tareaspring.demo.repository;

import com.tareaspring.demo.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByCif(String cif);
    Optional<Empresa> findByNombre(String nombre);
}
