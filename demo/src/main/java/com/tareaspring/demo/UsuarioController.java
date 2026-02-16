package com.tareaspring.demo;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
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
        Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
        usuario.setEmpresa(empresa);
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        if (usuario.getRole() == null) {
            usuario.setRole("ROLE_USER");
        }
        usuarioRepository.save(usuario);
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
