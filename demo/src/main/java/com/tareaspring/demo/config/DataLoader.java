package com.tareaspring.demo.config;

import com.tareaspring.demo.model.Empresa;
import com.tareaspring.demo.model.Usuario;
import com.tareaspring.demo.repository.EmpresaRepository;
import com.tareaspring.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.dataloader.enabled:true}")
    private boolean dataLoaderEnabled;

    public DataLoader(EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!dataLoaderEnabled) {
            return;
        }

        Empresa empresa1 = createEmpresaIfMissing(
                "Tech Solutions S.L.", "123456780", "Calle Principal 123", "Madrid", "28001", "912345678", "info@techsolutions.es"
        );
        Empresa empresa2 = createEmpresaIfMissing(
                "Digital Innovations S.A.", "876543210", "Avenida Tecnologica 456", "Barcelona", "08002", "933456789", "contacto@digitalinnovations.es"
        );
        createEmpresaIfMissing(
                "Global Consulting Group", "567123450", "Carrer de les Terres 67", "Valencia", "46005", "963456789", "consultoria@globalconsulting.com"
        );
        createEmpresaIfMissing(
                "Ecomerce Solutions S.L.", "112233440", "Calle Comercio 789", "Sevilla", "41001", "954123456", "ventas@ecomerce-solutions.com"
        );
        createEmpresaIfMissing(
                "Digital Marketing Agency", "443322110", "Paseo del Marketing 22", "Zaragoza", "50003", "976543210", "info@dmagency.com"
        );
        createEmpresaIfMissing(
                "CyberTech Labs S.A.", "223344550", "Calle de la Seguridad 101", "Bilbao", "48001", "944332211", "info@cybertechlabs.es"
        );

        createUserIfMissing("admin@admin.es", "admin", "admin", "612345678", "admin", "ROLE_ADMIN", empresa1);
        createUserIfMissing("juana@tech.es", "Juana", "Perez", "612345678", "password1", "ROLE_ADMIN", empresa1);
        createUserIfMissing("maria@tech.es", "Maria", "Garcia", "623456789", "password2", "ROLE_USER", empresa1);
        createUserIfMissing("carlos@digit.es", "Carlos", "Lopez", "634567890", "password3", "ROLE_USER", empresa2);
        createUserIfMissing("anabel@digit.es", "Anabel", "Martin", "645678901", "password4", "ROLE_USER", empresa2);

        System.out.println("DataLoader aplicado: empresas/usuarios faltantes insertados");
        System.out.println("Accede a la consola H2 en: http://localhost:8080/h2-console");
    }

    private Empresa createEmpresaIfMissing(String nombre, String cif, String direccion, String ciudad, String codigoPostal, String telefono, String email) {
        Empresa existingByCif = empresaRepository.findByCif(cif).orElse(null);
        if (existingByCif != null) {
            return existingByCif;
        }

        Empresa existingByName = empresaRepository.findByNombre(nombre).orElse(null);
        if (existingByName != null) {
            return existingByName;
        }

        Empresa empresa = new Empresa(nombre, cif, direccion, ciudad, codigoPostal, telefono, email);
        return empresaRepository.save(empresa);
    }

    private void createUserIfMissing(String email, String nombre, String apellido, String telefono, String rawPassword, String role, Empresa empresa) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            return;
        }

        Usuario usuario = new Usuario(email, nombre, apellido, telefono, empresa);
        usuario.setRole(role);
        usuario.setPassword(passwordEncoder.encode(rawPassword));
        usuarioRepository.save(usuario);
    }
}
