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
        model.addAttribute("usuarioDto", new com.tareaspring.demo.service.UsuarioCreateDto());
        model.addAttribute("selectedEmpresaId", null);
        model.addAttribute("empresas", empresaService.findAll());
        model.addAttribute("roles", java.util.List.of("ROLE_USER","ROLE_ADMIN"));
        model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
        return "usuario_form";
    }

    @GetMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public String listUsuarios(@RequestParam(value = "page", defaultValue = "0") int page, Model model, Principal principal) {
        Page<Usuario> usuariosPage = usuarioService.findPage(page, 5);

        model.addAttribute("usuarios", usuariosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usuariosPage.getTotalPages());
        model.addAttribute("currentUserEmail", principal != null ? principal.getName() : null);
        return "usuario_admin";
    }

    @GetMapping("/{id}/edit")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public String editUsuario(@PathVariable Long id, Model model, Principal principal) {
        Usuario usuario = usuarioService.findById(id);
        com.tareaspring.demo.service.UsuarioUpdateDto dto = new com.tareaspring.demo.service.UsuarioUpdateDto();
        if (usuario != null) {
            dto.setEmail(usuario.getEmail());
            dto.setNombre(usuario.getNombre());
            dto.setApellido(usuario.getApellido());
            dto.setTelefono(usuario.getTelefono());
            dto.setEmpresaId(usuario.getEmpresa() != null ? usuario.getEmpresa().getId() : null);
            dto.setRole(usuario.getRole());
        }
        model.addAttribute("usuarioUpdateDto", dto);
        model.addAttribute("usuario", usuario);
        model.addAttribute("selectedEmpresaId", usuario != null && usuario.getEmpresa() != null ? usuario.getEmpresa().getId() : null);
        model.addAttribute("empresas", empresaService.findAll());
        model.addAttribute("roles", java.util.List.of("ROLE_USER","ROLE_ADMIN"));
        model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
        return "usuario_edit";
    }

    @PostMapping
    public String createUsuario(@Valid @ModelAttribute("usuarioDto") com.tareaspring.demo.service.UsuarioCreateDto usuarioDto,
                                BindingResult bindingResult,
                                @RequestParam(value = "empresaId", required = false) Long empresaId,
                                Model model,
                                Principal principal,
                                org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("selectedEmpresaId", empresaId);
            model.addAttribute("empresas", empresaService.findAll());
            model.addAttribute("roles", java.util.List.of("ROLE_USER","ROLE_ADMIN"));
            model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
            return "usuario_form";
        }

        // server-side checks
        if (usuarioDto.getPassword() == null || usuarioDto.getPassword().length() < 6) {
            bindingResult.rejectValue("password", "password.tooShort", "La contraseña debe tener al menos 6 caracteres");
        }
        if (!("ROLE_USER".equals(usuarioDto.getRole()) || "ROLE_ADMIN".equals(usuarioDto.getRole()))) {
            bindingResult.rejectValue("role", "role.invalid", "Rol invalido");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("selectedEmpresaId", empresaId);
            model.addAttribute("empresas", empresaService.findAll());
            model.addAttribute("roles", java.util.List.of("ROLE_USER","ROLE_ADMIN"));
            model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
            return "usuario_form";
        }

        // map DTO to entity
        com.tareaspring.demo.model.Usuario usuario = new com.tareaspring.demo.model.Usuario();
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setTelefono(usuarioDto.getTelefono());
        usuario.setPassword(usuarioDto.getPassword());
        usuario.setConfirmPassword(usuarioDto.getConfirmPassword());
        usuario.setRole(usuarioDto.getRole());

        boolean autoLogin = (principal == null);
        com.tareaspring.demo.service.UsuarioCreationStatus status = usuarioService.create(usuario, empresaId, autoLogin);

        switch (status) {
            case EMAIL_ALREADY_EXISTS:
                ra.addFlashAttribute("errorMessage", "El email ya está registrado.");
                return "redirect:/usuarios/new";
            case USERNAME_ALREADY_EXISTS:
                ra.addFlashAttribute("errorMessage", "El nombre de usuario ya está registrado.");
                return "redirect:/usuarios/new";
            case EMPRESA_NOT_FOUND:
                ra.addFlashAttribute("errorMessage", "Debes seleccionar una empresa valida.");
                return "redirect:/usuarios/new";
            case PASSWORD_REQUIRED:
                ra.addFlashAttribute("errorMessage", "La contraseña es obligatoria.");
                return "redirect:/usuarios/new";
            case PASSWORDS_DO_NOT_MATCH:
                ra.addFlashAttribute("errorMessage", "Las contraseñas no coinciden.");
                return "redirect:/usuarios/new";
            case AUTHENTICATION_FAILED:
                ra.addFlashAttribute("errorMessage", "Fallo de autenticación.");
                return "redirect:/login";
            case SUCCESS:
            default:
                if (principal != null) {
                    ra.addFlashAttribute("successMessage", "Usuario creado correctamente.");
                    return "redirect:/usuarios";
                }
                ra.addFlashAttribute("successMessage", "Registro exitoso, iniciando sesión...");
                return "redirect:/usuarios/new";
        }
    }

    @PostMapping("/{id}")
    public String updateUsuario(@PathVariable Long id,
                                @Valid @ModelAttribute("usuarioUpdateDto") com.tareaspring.demo.service.UsuarioUpdateDto usuarioDto,
                                BindingResult bindingResult,
                                @RequestParam(value = "empresaId", required = false) Long empresaId,
                                Model model,
                                Principal principal,
                                org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("selectedEmpresaId", empresaId);
            model.addAttribute("empresas", empresaService.findAll());
            model.addAttribute("roles", java.util.List.of("ROLE_USER","ROLE_ADMIN"));
            model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
            return "usuario_edit";
        }
        // map DTO to entity for update
        com.tareaspring.demo.model.Usuario usuario = new com.tareaspring.demo.model.Usuario();
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setTelefono(usuarioDto.getTelefono());
        usuario.setRole(usuarioDto.getRole());

        usuarioService.update(id, usuario, empresaId);
        ra.addFlashAttribute("successMessage", "Usuario actualizado correctamente.");
        return "redirect:/usuarios";
    }

    @GetMapping("/{id}/change-password")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public String changePasswordForm(@PathVariable Long id, Model model, Principal principal) {
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("passwordChangeDto", new com.tareaspring.demo.service.PasswordChangeDto());
        model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
        return "usuario_change_password";
    }

    @PostMapping("/{id}/change-password")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public String changePassword(@PathVariable Long id,
                                 @Valid @ModelAttribute("passwordChangeDto") com.tareaspring.demo.service.PasswordChangeDto passwordChangeDto,
                                 BindingResult bindingResult,
                                 Model model,
                                 Principal principal,
                                 org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuarioService.findById(id));
            model.addAttribute("volverUrl", principal == null ? "/" : "/usuarios");
            return "usuario_change_password";
        }
        String newPassword = passwordChangeDto.getPassword();
        String confirm = passwordChangeDto.getConfirmPassword();
        com.tareaspring.demo.service.UsuarioCreationStatus status = usuarioService.changePassword(id, newPassword, confirm);
        switch (status) {
            case PASSWORD_REQUIRED:
                ra.addFlashAttribute("errorMessage", "La contraseña es obligatoria.");
                return "redirect:/usuarios/" + id + "/change-password";
            case PASSWORDS_DO_NOT_MATCH:
                ra.addFlashAttribute("errorMessage", "Las contraseñas no coinciden.");
                return "redirect:/usuarios/" + id + "/change-password";
            case SUCCESS:
            default:
                ra.addFlashAttribute("successMessage", "Contraseña actualizada correctamente.");
                return "redirect:/usuarios";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteUsuario(@PathVariable Long id, Principal principal, org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {
        if (principal != null) {
            Usuario current = usuarioService.findByEmail(principal.getName());
            if (current != null && current.getId() != null && current.getId().equals(id)) {
                // Prevent deleting self
                ra.addFlashAttribute("errorMessage", "No puedes borrar el usuario con el que has iniciado sesión.");
                return "redirect:/usuarios";
            }
        }
        usuarioService.deleteById(id);
        ra.addFlashAttribute("successMessage", "Usuario eliminado correctamente.");
        return "redirect:/usuarios";
    }
}
