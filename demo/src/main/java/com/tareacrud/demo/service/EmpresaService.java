package com.tareacrud.demo.service;

import com.tareacrud.demo.entity.Empresa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpresaService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    // Obtener todas las empresas
    public List<Empresa> obtenerTodas() {
        TypedQuery<Empresa> query = entityManager.createQuery("SELECT e FROM Empresa e", Empresa.class);
        return query.getResultList();
    }
    
    // Obtener empresa por ID
    public Optional<Empresa> obtenerPorId(Long id) {
        Empresa empresa = entityManager.find(Empresa.class, id);
        return Optional.ofNullable(empresa);
    }
    
    // Obtener empresa por NIF
    public Optional<Empresa> obtenerPorNif(String nif) {
        TypedQuery<Empresa> query = entityManager.createQuery("SELECT e FROM Empresa e WHERE e.nif = :nif", Empresa.class);
        query.setParameter("nif", nif);
        List<Empresa> resultado = query.getResultList();
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }
    
    // Obtener empresa por nombre
    public Optional<Empresa> obtenerPorNombre(String nombre) {
        TypedQuery<Empresa> query = entityManager.createQuery("SELECT e FROM Empresa e WHERE e.nombre = :nombre", Empresa.class);
        query.setParameter("nombre", nombre);
        List<Empresa> resultado = query.getResultList();
        return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
    }
    
    // Crear nueva empresa
    public Empresa crear(Empresa empresa) {
        entityManager.persist(empresa);
        return empresa;
    }
    
    // Actualizar empresa
    public Empresa actualizar(Long id, Empresa empresaActualizada) {
        Empresa empresa = entityManager.find(Empresa.class, id);
        
        if (empresa != null) {
            empresa.setNombre(empresaActualizada.getNombre());
            empresa.setNif(empresaActualizada.getNif());
            
            entityManager.merge(empresa);
            return empresa;
        }
        
        return null;
    }
    
    // Eliminar empresa
    public void eliminar(Long id) {
        Empresa empresa = entityManager.find(Empresa.class, id);
        if (empresa != null) {
            entityManager.remove(empresa);
        }
    }
}
