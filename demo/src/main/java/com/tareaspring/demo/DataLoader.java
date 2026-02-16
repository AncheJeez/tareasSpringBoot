package com.tareaspring.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class DataLoader implements CommandLineRunner {

        private final EmpresaRepository empresaRepository;
        private final UsuarioRepository usuarioRepository;
        private final PasswordEncoder passwordEncoder;

        public DataLoader(EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
                this.empresaRepository = empresaRepository;
                this.usuarioRepository = usuarioRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) throws Exception {
        // Crear empresas
        Empresa empresa1 = new Empresa(
                "Tech Solutions S.L.",
                "B12345678",
                "Calle Principal 123",
                "Madrid",
                "28001",
                "912345678",
                "info@techsolutions.es"
        );
        empresaRepository.save(empresa1);

        Empresa empresa2 = new Empresa(
                "Digital Innovations S.A.",
                "A87654321",
                "Avenida Tecnológica 456",
                "Barcelona",
                "08002",
                "933456789",
                "contacto@digitalinnovations.es"
        );
        empresaRepository.save(empresa2);

        Empresa empresa3 = new Empresa(
                "Global Consulting Group",
                "B56712345",
                "Carrer de les Terres 67",
                "Valencia",
                "46005",
                "963456789",
                "consultoria@globalconsulting.com"
        );
        empresaRepository.save(empresa3);

        Empresa empresa4 = new Empresa(
                "Ecomerce Solutions S.L.",
                "B11223344",
                "Calle Comercio 789",
                "Sevilla",
                "41001",
                "954123456",
                "ventas@ecomerce-solutions.com"
        );
        empresaRepository.save(empresa4);

        Empresa empresa5 = new Empresa(
                "Digital Marketing Agency",
                "B44332211",
                "Paseo del Marketing 22",
                "Zaragoza",
                "50003",
                "976543210",
                "info@dmagency.com"
        );
        empresaRepository.save(empresa5);

        Empresa empresa6 = new Empresa(
                "CyberTech Labs S.A.",
                "B22334455",
                "Calle de la Seguridad 101",
                "Bilbao",
                "48001",
                "944332211",
                "info@cybertechlabs.es"
        );
        empresaRepository.save(empresa6);

        // Crear usuarios para la primera empresa
        Usuario usuario1 = new Usuario(
                "juan.perez@techsolutions.es",
                "Juan",
                "Pérez",
                "612345678",
                empresa1
        );
        usuario1.setRole("ROLE_ADMIN");

        Usuario usuario2 = new Usuario(
                "maria.garcia@techsolutions.es",
                "María",
                "García",
                "623456789",
                empresa1
        );

        // Crear usuarios para la segunda empresa
        Usuario usuario3 = new Usuario(
                "carlos.lopez@digitalinnovations.es",
                "Carlos",
                "López",
                "634567890",
                empresa2
        );

        Usuario usuario4 = new Usuario(
                "ana.martinez@digitalinnovations.es",
                "Ana",
                "Martínez",
                "645678901",
                empresa2
        );

        // Establecer contraseñas codificadas y roles
        usuario1.setPassword(passwordEncoder.encode("password1"));
        usuario2.setPassword(passwordEncoder.encode("password2"));
        usuario3.setPassword(passwordEncoder.encode("password3"));
        usuario4.setPassword(passwordEncoder.encode("password4"));

        // Guardar usuarios
        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
        usuarioRepository.save(usuario3);
        usuarioRepository.save(usuario4);

        System.out.println("Base de datos inicializada con datos de ejemplo");
        System.out.println("2 empresas y 4 usuarios creados");
        System.out.println("Accede a la consola H2 en: http://localhost:8080/h2-console");
    }
}
