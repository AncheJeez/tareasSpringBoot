package com.tareaspring.demo;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;

    public EmpresaController(EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository) {
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/new")
    public String newEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresa_form";
    }

    // @GetMapping
    // public String listEmpresas(Model model) {
    //     model.addAttribute("empresas", empresaRepository.findAll());
    //     return "empresas_admin";
    // }
    @GetMapping
    public String listEmpresas(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Empresa> empresasPage = empresaRepository.findAll(pageable);
        
        model.addAttribute("empresas", empresasPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", empresasPage.getTotalPages());
        return "empresas_admin";
    }

    @GetMapping("/{id}/edit")
    public String editEmpresa(@PathVariable Long id, Model model) {
        Empresa empresa = empresaRepository.findById(id).orElse(null);
        if (empresa != null) {
            model.addAttribute("empresa", empresa);
            model.addAttribute("usuarios", empresa.getUsuarios());
        }
        return "empresa_form";
    }

    @PostMapping
    public String createEmpresa(@ModelAttribute Empresa empresa) {
        empresaRepository.save(empresa);
        return "redirect:/empresas";
    }

    @PostMapping("/{id}")
    public String updateEmpresa(@PathVariable Long id, @ModelAttribute Empresa empresa) {
        Empresa empresaExistente = empresaRepository.findById(id).orElse(null);
        if (empresaExistente != null) {
            empresaExistente.setNombre(empresa.getNombre());
            empresaExistente.setCif(empresa.getCif());
            empresaExistente.setCiudad(empresa.getCiudad());
            empresaExistente.setTelefono(empresa.getTelefono());
            empresaExistente.setEmail(empresa.getEmail());
            empresaRepository.save(empresaExistente); 

            for (Usuario usuario : empresaExistente.getUsuarios()) {
                usuario.setEmpresa(empresaExistente);
                usuarioRepository.save(usuario);
            }
        }
        return "redirect:/empresas";
    }

    @PostMapping("/{id}/delete")
    public String deleteEmpresa(@PathVariable Long id) {
        empresaRepository.deleteById(id);
        return "redirect:/empresas";
    }
}
