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

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    @org.springframework.transaction.annotation.Transactional
    public UsuarioCreationStatus create(Usuario usuario, Long empresaId, boolean autoLogin) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return UsuarioCreationStatus.EMAIL_ALREADY_EXISTS;
        }

        if (usuarioRepository.findByNombre(usuario.getNombre()).isPresent()) {
            return UsuarioCreationStatus.USERNAME_ALREADY_EXISTS;
        }

        Empresa empresa = null;
        if (empresaId != null) {
            empresa = empresaRepository.findById(empresaId).orElse(null);
            if (empresa == null) {
                return UsuarioCreationStatus.EMPRESA_NOT_FOUND;
            }
        }

        String rawPassword = usuario.getPassword();
        String confirm = usuario.getConfirmPassword();
        if (rawPassword == null || rawPassword.isEmpty()) {
            return UsuarioCreationStatus.PASSWORD_REQUIRED;
        }
        if (confirm == null || !rawPassword.equals(confirm)) {
            return UsuarioCreationStatus.PASSWORDS_DO_NOT_MATCH;
        }

        if (empresa != null) {
            usuario.setEmpresa(empresa);
        } else {
            usuario.setEmpresa(null);
        }
        usuario.setPassword(passwordEncoder.encode(rawPassword));
        if (usuario.getRole() == null) {
            usuario.setRole("ROLE_USER");
        }
        usuarioRepository.save(usuario);

        if (autoLogin) {
            try {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(usuario.getEmail(), rawPassword);
                Authentication authentication = authenticationManager.authenticate(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                return UsuarioCreationStatus.AUTHENTICATION_FAILED;
            }
        }

        return UsuarioCreationStatus.SUCCESS;
    }

    @org.springframework.transaction.annotation.Transactional
    public void update(Long id, Usuario usuario, Long empresaId) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setTelefono(usuario.getTelefono());
        // Only change password via changePassword flow
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            // ignore: do not change password here to enforce separate flow
        }
        if (empresaId != null) {
            Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
            if (empresa != null) {
                usuarioExistente.setEmpresa(empresa);
            } else {
                usuarioExistente.setEmpresa(null);
            }
        } else {
            usuarioExistente.setEmpresa(null);
        }
        // update role only if valid
        if (usuario.getRole() != null && ("ROLE_USER".equals(usuario.getRole()) || "ROLE_ADMIN".equals(usuario.getRole()))) {
            usuarioExistente.setRole(usuario.getRole());
        }
        usuarioRepository.save(usuarioExistente);
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    @org.springframework.transaction.annotation.Transactional
    public UsuarioCreationStatus changePassword(Long id, String newPassword, String confirmPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            return UsuarioCreationStatus.PASSWORD_REQUIRED;
        }
        if (confirmPassword == null || !newPassword.equals(confirmPassword)) {
            return UsuarioCreationStatus.PASSWORDS_DO_NOT_MATCH;
        }
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioExistente.setPassword(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuarioExistente);
        return UsuarioCreationStatus.SUCCESS;
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

    public List<String> getDistinctEmpresaCiudades() {
        return empresaRepository.findDistinctCiudades();
    }

    public List<Usuario> findUsuariosByEmpresaCiudad(String ciudad) {
        return usuarioRepository.findByEmpresaCiudad(ciudad);
    }

    public java.util.List<Usuario> findUsuariosByRole(String role) {
        return usuarioRepository.findByRole(role, org.springframework.data.domain.PageRequest.of(0, 1000)).getContent();
    }
}
