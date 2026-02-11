package com.demo.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.demo.entidad.Empresa;
import com.demo.service.EmpresaService;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService service;

    public EmpresaController(EmpresaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Page<Empresa> empresasPage = service.listarPaginado(
                PageRequest.of(page, size, Sort.by("nombre").ascending())
        );

        model.addAttribute("empresasPage", empresasPage);
        model.addAttribute("empresas", empresasPage.getContent());

        return "empresas/list";
    }


    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/form";
    }


    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("empresa") Empresa empresa,
            BindingResult result,
            Model model) {

        // Si hay errores de validación → volver al form
        if (result.hasErrors()) {
            return "empresas/form";
        }

        service.guardar(empresa);

        return "redirect:/empresas";
    }


    // ===============================
    // EDITAR
    // ===============================
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {

        Empresa empresa = service.buscarPorId(id);

        if (empresa == null) {
            return "redirect:/empresas";
        }

        model.addAttribute("empresa", empresa);
        return "empresas/form";
    }


    // ===============================
    // ELIMINAR
    // ===============================
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return "redirect:/empresas";
    }
}
