package com.tareaspring.demo.controller;

import com.tareaspring.demo.service.UsuarioService;
import com.tareaspring.demo.service.EmpresaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;

    public ReporteController(UsuarioService usuarioService, EmpresaService empresaService) {
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
    }

    @GetMapping("/usuarios-por-ciudad")
    public String usuariosPorCiudad(@org.springframework.web.bind.annotation.RequestParam(value = "empresaCiudad", required = false) String empresaCiudad,
                                    @org.springframework.web.bind.annotation.RequestParam(value = "userRole", required = false) String userRole,
                                    Model model) {
        model.addAttribute("title", "Reportes");
        model.addAttribute("roles", java.util.List.of("ROLE_USER","ROLE_ADMIN"));
        model.addAttribute("empresaCiudades", usuarioService.getDistinctEmpresaCiudades());

        if ((empresaCiudad == null || empresaCiudad.isBlank()) && (userRole == null || userRole.isBlank())) {
            model.addAttribute("stats", usuarioService.getUsuariosPorCiudadStats());
        } else {
            if (empresaCiudad != null && !empresaCiudad.isBlank()) {
                model.addAttribute("filteredUsersByEmpresaCiudad", usuarioService.findUsuariosByEmpresaCiudad(empresaCiudad));
                model.addAttribute("empresasInCiudad", empresaService.findByCiudad(empresaCiudad));
            }
            if (userRole != null && !userRole.isBlank()) {
                model.addAttribute("filteredUsersByRole", usuarioService.findUsuariosByRole(userRole));
            }
        }

        model.addAttribute("selectedEmpresaCiudad", empresaCiudad);
        model.addAttribute("selectedUserRole", userRole);
        return "reporte_usuarios_ciudad";
    }
}
