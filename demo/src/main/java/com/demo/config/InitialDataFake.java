package com.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.demo.entidad.Game;
import com.demo.entidad.Usuario;
import com.demo.entidad.enumerado.Rol;
import com.demo.service.EmpresaService;
import com.demo.service.UsuarioServicio;
import com.demo.entidad.Empresa;

@Component
public class InitialDataFake implements CommandLineRunner {

  private final int TOTAL_EMPRESAS = 100;

  private final UsuarioServicio usuarioServicio;
  private final EmpresaService empresaService;
  private final Faker faker;

  public InitialDataFake(UsuarioServicio usuarioServicio, EmpresaService empresaService) {
    this.usuarioServicio = usuarioServicio;
    this.empresaService = empresaService;
    this.faker = new Faker();
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("========================================");
    System.out.println("Iniciando carga de datos de prueba...");
    System.out.println("========================================");

    // Crear usuarios
    crearUsuarios();

    // Crear productos
    crearEmpresas();

    System.out.println("========================================");
    System.out.println("Carga de datos completada exitosamente!");
    System.out.println("========================================");
  }

  private void crearUsuarios() {
    System.out.println("Creando usuarios...");
    
    try {
      usuarioServicio.crear("admin", "admin123", Rol.ADMIN);
      System.out.println("Usuario ADMIN creado (usuario: admin, contraseña: admin123)");
    } catch (IllegalArgumentException e) {
      System.out.println("Usuario ADMIN ya existe");
    }

    try {
      usuarioServicio.crear("manager", "manager123", Rol.MANAGER);
      System.out.println("Usuario MANAGER creado (usuario: manager, contraseña: manager123)");
    } catch (IllegalArgumentException e) {
      System.out.println("Usuario MANAGER ya existe");
    }

    try {
      usuarioServicio.crear("usuario", "usuario123", Rol.USUARIO);
      System.out.println("Usuario USUARIO creado (usuario: usuario, contraseña: usuario123)");
    } catch (IllegalArgumentException e) {
      System.out.println("Usuario USUARIO ya existe");
    }

    System.out.println();
  }

  private void crearEmpresas() {
    System.out.println("Creando " + TOTAL_EMPRESAS + " empresas de prueba...");

    long empresasExistentes = empresaService.listarTodos().size();
    if (empresasExistentes >= TOTAL_EMPRESAS) {
        System.out.println("Ya existen " + empresasExistentes + " empresas. No se crearán más.");
        return;
    }

    for (int i = 0; i < TOTAL_EMPRESAS; i++) {
        Empresa empresa = new Empresa();

        empresa.setNombre(faker.company().name());
        empresa.setNif(faker.idNumber().valid());
        empresa.setDireccionSocial(faker.address().fullAddress());
        empresa.setRazonSocial(faker.company().name());
        empresa.setCapitalSocial(faker.number().randomDouble(2, 1000, 1000000));
        empresa.setFechaConstitucion(faker.date().birthday(1, 100).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());

        empresaService.guardar(empresa);

        if ((i + 1) % 10 == 0) {
            System.out.println(" Creadas " + (i + 1) + " empresas...");
        }
    }

    System.out.println("Total de empresas creadas: " + TOTAL_EMPRESAS);
    System.out.println();
	}

}