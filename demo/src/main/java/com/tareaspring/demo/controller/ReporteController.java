package com.tareaspring.demo.controller;

import com.tareaspring.demo.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    private final UsuarioService usuarioService;

    public ReporteController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios-por-ciudad")
    public String usuariosPorCiudad(Model model) {
        model.addAttribute("stats", usuarioService.getUsuariosPorCiudadStats());
        return "reporte_usuarios_ciudad";
    }
}
