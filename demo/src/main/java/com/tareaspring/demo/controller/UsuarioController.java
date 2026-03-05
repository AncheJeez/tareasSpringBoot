package com.tareaspring.demo.controller;

import com.tareaspring.demo.model.Usuario;
import com.tareaspring.demo.service.EmpresaService;
import com.tareaspring.demo.service.UsuarioCreationStatus;
import com.tareaspring.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;

    public UsuarioController(UsuarioService usuarioService, EmpresaService empresaService) {
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
    }

    @GetMapping("/new")
    public String newUsuario(Model model, Principal principal) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("selectedEmpresaId", null);
        model.addAttribute("empresas", empresaService.findAll());
        model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
        return "usuario_form";
    }

    @GetMapping
    public String listUsuarios(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<Usuario> usuariosPage = usuarioService.findPage(page, 5);

        model.addAttribute("usuarios", usuariosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usuariosPage.getTotalPages());
        return "usuario_admin";
    }

    @GetMapping("/{id}/edit")
    public String editUsuario(@PathVariable Long id, Model model, Principal principal) {
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("selectedEmpresaId", usuario != null && usuario.getEmpresa() != null ? usuario.getEmpresa().getId() : null);
        model.addAttribute("empresas", empresaService.findAll());
        model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
        return "usuario_form";
    }

    @PostMapping
    public String createUsuario(@Valid @ModelAttribute Usuario usuario,
                                BindingResult bindingResult,
                                @RequestParam(value = "empresaId", required = false) Long empresaId,
                                Model model,
                                Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("selectedEmpresaId", empresaId);
            model.addAttribute("empresas", empresaService.findAll());
            model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
            return "usuario_form";
        }

        UsuarioCreationStatus status = usuarioService.create(usuario, empresaId);

        switch (status) {
            case EMAIL_ALREADY_EXISTS:
                return "redirect:/usuarios/new?error=email";
            case EMPRESA_NOT_FOUND:
                model.addAttribute("selectedEmpresaId", empresaId);
                model.addAttribute("empresas", empresaService.findAll());
                model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
                model.addAttribute("empresaError", "Debes seleccionar una empresa valida.");
                return "usuario_form";
            case PASSWORD_REQUIRED:
                return "redirect:/usuarios/new?error=password";
            case AUTHENTICATION_FAILED:
                return "redirect:/login?error=true";
            case SUCCESS:
            default:
                return "redirect:/";
        }
    }

    @PostMapping("/{id}")
    public String updateUsuario(@PathVariable Long id,
                                @Valid @ModelAttribute Usuario usuario,
                                BindingResult bindingResult,
                                @RequestParam(value = "empresaId", required = false) Long empresaId,
                                Model model,
                                Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("selectedEmpresaId", empresaId);
            model.addAttribute("empresas", empresaService.findAll());
            model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
            return "usuario_form";
        }
        if (empresaId == null) {
            model.addAttribute("selectedEmpresaId", null);
            model.addAttribute("empresas", empresaService.findAll());
            model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
            model.addAttribute("empresaError", "Debes seleccionar una empresa valida.");
            return "usuario_form";
        }

        usuarioService.update(id, usuario, empresaId);
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/delete")
    public String deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return "redirect:/usuarios";
    }
}
