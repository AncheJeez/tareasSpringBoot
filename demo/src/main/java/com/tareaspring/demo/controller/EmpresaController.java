package com.tareaspring.demo.controller;

import com.tareaspring.demo.model.Empresa;
import com.tareaspring.demo.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/new")
    public String newEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());
        model.addAttribute("ciudades", empresaService.getDistinctCiudades());
        return "empresa_form";
    }

    @GetMapping
    public String listEmpresas(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<Empresa> empresasPage = empresaService.findPage(page, 5);

        model.addAttribute("empresas", empresasPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", empresasPage.getTotalPages());
        return "empresas_admin";
    }

    @GetMapping("/{id}/edit")
    public String editEmpresa(@PathVariable Long id, Model model) {
        Empresa empresa = empresaService.findById(id);
        if (empresa != null) {
            model.addAttribute("empresa", empresa);
            model.addAttribute("usuarios", empresa.getUsuarios());
        }
        model.addAttribute("ciudades", empresaService.getDistinctCiudades());
        return "empresa_form";
    }

    @PostMapping
    public String createEmpresa(@Valid @ModelAttribute Empresa empresa, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ciudades", empresaService.getDistinctCiudades());
            return "empresa_form";
        }
        empresaService.save(empresa);
        return "redirect:/empresas";
    }

    @PostMapping("/{id}")
    public String updateEmpresa(@PathVariable Long id, @Valid @ModelAttribute Empresa empresa, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ciudades", empresaService.getDistinctCiudades());
            return "empresa_form";
        }
        empresaService.update(id, empresa);
        return "redirect:/empresas";
    }

    @PostMapping("/{id}/delete")
    public String deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteById(id);
        return "redirect:/empresas";
    }
}
