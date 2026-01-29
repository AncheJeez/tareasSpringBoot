package com.tareacrud.demo.controller;

import com.tareacrud.demo.entity.Empresa;
import com.tareacrud.demo.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {
    
    @Autowired
    private EmpresaService empresaService;
    
    // Obtener todas las empresas
    @GetMapping
    public ResponseEntity<List<Empresa>> obtenerTodas() {
        List<Empresa> empresas = empresaService.obtenerTodas();
        return ResponseEntity.ok(empresas);
    }
    
    // Obtener empresa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerPorId(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.obtenerPorId(id);
        return empresa.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Obtener empresa por NIF
    @GetMapping("/nif/{nif}")
    public ResponseEntity<Empresa> obtenerPorNif(@PathVariable String nif) {
        Optional<Empresa> empresa = empresaService.obtenerPorNif(nif);
        return empresa.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Obtener empresa por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Empresa> obtenerPorNombre(@PathVariable String nombre) {
        Optional<Empresa> empresa = empresaService.obtenerPorNombre(nombre);
        return empresa.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Actualizar empresa
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> actualizar(@PathVariable Long id, @RequestBody Empresa empresaActualizada) {
        Empresa empresa = empresaService.actualizar(id, empresaActualizada);
        if (empresa != null) {
            return ResponseEntity.ok(empresa);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Eliminar empresa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.obtenerPorId(id);
        if (empresa.isPresent()) {
            empresaService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
