package com.tareaspring.demo.service;

import com.tareaspring.demo.model.Empresa;
import com.tareaspring.demo.model.Usuario;
import com.tareaspring.demo.repository.EmpresaRepository;
import com.tareaspring.demo.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UsuarioService(UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Page<Usuario> findPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return usuarioRepository.findAll(pageable);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public UsuarioCreationStatus create(Usuario usuario, Long empresaId) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return UsuarioCreationStatus.EMAIL_ALREADY_EXISTS;
        }

        if (empresaId == null) {
            return UsuarioCreationStatus.EMPRESA_NOT_FOUND;
        }

        Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
        if (empresa == null) {
            return UsuarioCreationStatus.EMPRESA_NOT_FOUND;
        }

        String rawPassword = usuario.getPassword();
        if (rawPassword == null || rawPassword.isEmpty()) {
            return UsuarioCreationStatus.PASSWORD_REQUIRED;
        }

        usuario.setEmpresa(empresa);
        usuario.setPassword(passwordEncoder.encode(rawPassword));
        if (usuario.getRole() == null) {
            usuario.setRole("ROLE_USER");
        }
        usuarioRepository.save(usuario);

        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(usuario.getEmail(), rawPassword);
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            return UsuarioCreationStatus.AUTHENTICATION_FAILED;
        }

        return UsuarioCreationStatus.SUCCESS;
    }

    public void update(Long id, Usuario usuario, Long empresaId) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente != null) {
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setTelefono(usuario.getTelefono());
            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            if (empresaId != null) {
                Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
                if (empresa != null) {
                    usuarioExistente.setEmpresa(empresa);
                }
            }
            usuarioRepository.save(usuarioExistente);
        }
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<CiudadUsuariosStats> getUsuariosPorCiudadStats() {
        List<UsuarioRepository.UsuarioPorCiudadProjection> data = usuarioRepository.countUsuariosByCiudad();
        long max = data.stream().mapToLong(UsuarioRepository.UsuarioPorCiudadProjection::getTotal).max().orElse(0L);

        return data.stream()
                .map(item -> {
                    int porcentaje = max == 0 ? 0 : (int) Math.round((item.getTotal() * 100.0) / max);
                    return new CiudadUsuariosStats(item.getCiudad(), item.getTotal(), porcentaje);
                })
                .toList();
    }
}
