package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.entidad.Empresa;
import com.demo.repository.EmpresaRepository;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository repository;

    public EmpresaServiceImpl(EmpresaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Empresa guardar(Empresa empresa) {
        return repository.save(empresa);
    }

    @Override
    public List<Empresa> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Page<Empresa> listarPaginado(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Empresa buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Empresa actualizar(Integer id, Empresa empresa) {
        Optional<Empresa> existente = repository.findById(id);

        if (existente.isPresent()) {
            Empresa e = existente.get();
            e.setNombre(empresa.getNombre());
            e.setNif(empresa.getNif());
            e.setDireccionSocial(empresa.getDireccionSocial());
            e.setRazonSocial(empresa.getRazonSocial());
            e.setCapitalSocial(empresa.getCapitalSocial());
            e.setFechaConstitucion(empresa.getFechaConstitucion());
            return repository.save(e);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
