package com.tareacrud.demo.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    boolean existsByNif(String nif);
    
    Optional<Empresa> findByNif(String nif);
    
    Optional<Empresa> findByNombre(String nombre);
    
    List<Empresa> findByRazonSocial(String razonSocial);
    
    List<Empresa> findByDireccionSocialContaining(String direccion);
}
