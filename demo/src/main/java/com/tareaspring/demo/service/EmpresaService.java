package com.tareaspring.demo.service;

import com.tareaspring.demo.model.Empresa;
import com.tareaspring.demo.model.Usuario;
import com.tareaspring.demo.repository.EmpresaRepository;
import com.tareaspring.demo.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;

    public EmpresaService(EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository) {
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Page<Empresa> findPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return empresaRepository.findAll(pageable);
    }

    public Empresa findById(Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public void update(Long id, Empresa empresa) {
        Empresa empresaExistente = empresaRepository.findById(id).orElse(null);
        if (empresaExistente != null) {
            empresaExistente.setNombre(empresa.getNombre());
            empresaExistente.setCif(empresa.getCif());
            empresaExistente.setCiudad(empresa.getCiudad());
            empresaExistente.setTelefono(empresa.getTelefono());
            empresaExistente.setEmail(empresa.getEmail());
            empresaRepository.save(empresaExistente);

            for (Usuario usuario : empresaExistente.getUsuarios()) {
                usuario.setEmpresa(empresaExistente);
                usuarioRepository.save(usuario);
            }
        }
    }

    public void deleteById(Long id) {
        empresaRepository.deleteById(id);
    }

    public java.util.List<Empresa> findByCiudad(String ciudad) {
        return empresaRepository.findByCiudad(ciudad);
    }

    public java.util.List<String> getDistinctCiudades() {
        return empresaRepository.findDistinctCiudades();
    }
}
