package com.tareaspring.demo.repository;

import com.tareaspring.demo.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByCif(String cif);
    Optional<Empresa> findByNombre(String nombre);

    List<Empresa> findByCiudad(String ciudad);

    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT e.ciudad FROM Empresa e")
    List<String> findDistinctCiudades();
}
