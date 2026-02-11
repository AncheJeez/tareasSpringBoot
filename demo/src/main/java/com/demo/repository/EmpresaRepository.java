package com.demo.repository;

import com.demo.entidad.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

	List<Empresa> findByNombre(String nombre);
	Page<Empresa> findAll(Pageable pageable);
	Page<Empresa> findByNombre(String nombre, Pageable pageable);
	Page<Empresa> findByNombreContaining(String nombre, Pageable pageable);
}
