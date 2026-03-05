package com.tareaspring.demo.controller;

import com.tareaspring.demo.repository.UsuarioRepository;
import com.tareaspring.demo.model.Empresa;
import com.tareaspring.demo.model.Usuario;
import com.tareaspring.demo.repository.EmpresaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UsuarioController(UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/new")
    public String newUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("empresas", empresaRepository.findAll());
        return "usuario_form";
    }

    @GetMapping
    public String listUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuario_admin";
    }
    // como usuario no va a tener paginacion está comentado
    // @GetMapping
    // public String listUsuarios(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
    //     Pageable pageable = PageRequest.of(page, 5);
    //     Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);
        
    //     model.addAttribute("usuarios", usuariosPage.getContent());
    //     model.addAttribute("currentPage", page);
    //     model.addAttribute("totalPages", usuariosPage.getTotalPages());
    //     return "usuarios_admin";
    // }


    @GetMapping("/{id}/edit")
    public String editUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        model.addAttribute("usuario", usuario);
        model.addAttribute("empresas", empresaRepository.findAll());
        return "usuario_form";
    }

    @PostMapping
    public String createUsuario(@ModelAttribute Usuario usuario, @RequestParam("empresaId") Long empresaId) {
        // Verificar si el email ya existe
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return "redirect:/usuarios/new?error=email";
        }

        Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
        if (empresa == null) {
            return "redirect:/usuarios/new?error=empresa";
        }
        usuario.setEmpresa(empresa);
        String rawPassword = usuario.getPassword();
        if (rawPassword == null || rawPassword.isEmpty()) {
            return "redirect:/usuarios/new?error=password";
        }
        usuario.setPassword(passwordEncoder.encode(rawPassword));
        if (usuario.getRole() == null) {
            usuario.setRole("ROLE_USER");
        }
        usuarioRepository.save(usuario);

        // Autenticar al usuario recién creado
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuario.getEmail(), rawPassword);
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            // Si falla autenticación, redirigir a login
            return "redirect:/login?error=true";
        }

        return "redirect:/usuarios";
    }

    @PostMapping("/{id}")
    public String updateUsuario(@PathVariable Long id, @ModelAttribute Usuario usuario, @RequestParam("empresaId") Long empresaId) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente != null) {
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setTelefono(usuario.getTelefono());
            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
            usuarioExistente.setEmpresa(empresa);
            usuarioRepository.save(usuarioExistente);
        }
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/delete")
    public String deleteUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios";
    }

}
