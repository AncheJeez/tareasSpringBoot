package com.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.demo.entidad.Empresa;
import com.demo.repository.EmpresaRepository;

public interface EmpresaService {

    Empresa guardar(Empresa empresa);

    List<Empresa> listarTodos();

    Page<Empresa> listarPaginado(Pageable pageable);

    Empresa buscarPorId(Integer id);

    Empresa actualizar(Integer id, Empresa empresa);

    void eliminar(Integer id);
}
