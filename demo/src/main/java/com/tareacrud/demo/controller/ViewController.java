package com.tareacrud.demo.controller;

import com.tareacrud.demo.entity.Empresa;
import com.tareacrud.demo.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ViewController {
    
    @Autowired
    private EmpresaService empresaService;
    
    // Mostrar lista de empresas
    @GetMapping
    public String index(Model model) {
        List<Empresa> empresas = empresaService.obtenerTodas();
        model.addAttribute("empresas", empresas);
        return "index";
    }
    
    // Mostrar formulario para crear empresa
    @GetMapping("/crear")
    public String crearForm(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "crear";
    }
    
    // Guardar nueva empresa
    @PostMapping("/crear")
    public String crear(@ModelAttribute Empresa empresa) {
        empresaService.guardar(empresa);
        return "redirect:/";
    }
    
    // Mostrar formulario para editar empresa
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Optional<Empresa> empresa = empresaService.obtenerPorId(id);
        if (empresa.isPresent()) {
            model.addAttribute("empresa", empresa.get());
            return "editar";
        }
        return "redirect:/";
    }
    
    // Guardar cambios de empresa
    @PostMapping("/editar/{id}")
    public String editar(@PathVariable Long id, @ModelAttribute Empresa empresa) {
        empresa.setId(id);
        empresaService.guardar(empresa);
        return "redirect:/";
    }
    
    // Eliminar empresa
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        empresaService.eliminar(id);
        return "redirect:/";
    }
}
