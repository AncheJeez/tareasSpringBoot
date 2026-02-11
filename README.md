para activar postgre windows +r services.msc
localhost:8080

data.sql es el insert de prueba que introduzco en postgre pgadmin4

# CRUD MVC con Thymeleaf — RA3


## 1) Datos del alumno/a
- Entidad elegida -> Empresa

CREATE TABLE empresa (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    nif VARCHAR(20) NOT NULL UNIQUE,
    direccion_social VARCHAR(255),
    razon_social VARCHAR(255),
    capital_social NUMERIC(15,2),
    fecha_constitucion DATE
);

## 2) Repositorio (fork) y gestión de versiones
- Repositorio base: https://github.com/profeInformatica101/tareasSpringBoot
- Enlace a MI fork: https://github.com/AncheJeez/tareasSpringBoot
- Nº de commits realizados: (unos cuantos)

## 3) Arquitectura
Explica brevemente cómo has organizado:
- Controller: EmpresaController y ViewController
- Service: EmpresaService (Hace falta añadir una interfaz aquí)
- Repository: Está hecha la interfaz de la entidad Empresa
- Entity: Empresa

## 4) Base de datos elegida (marca una)
- H2 (Al principio intenté hacerlo con Postgre)

## 5) Configuración de la base de datos
### 5.1 Dependencias añadidas
(Indica la dependencia del driver que has usado)

### 5.2 application.properties / application.yml
he usado update no create, lo que significa que para que funcione se tiene que crear la base de datos manualmente
CREATE DATABASE empresa;

spring.jpa.hibernate.ddl-auto=update

### 5.3 Pasos para crear la BD (si aplica)
- Se crea automáticamente con unos datos de prueba

## 6) Cómo ejecutar el proyecto
1. Requisitos Java 17, Maven, H2
2. Comando de arranque:
      no uso comando, utilizo visual studio con la extensión de Springboot
3. URL de acceso:
   - http://localhost:9000/...

## 7) Pantallas / Rutas MVC
- / GET
- //crear GET
- //crear POST
- //editar/{id} GET
- //editar/{id} POST
- //eliminar/{id} GET
- /api/empresas/{id} DELETE
- /api/empresas/{id} GET
- /api/empresas/{id} PUT
- /api/empresas/nif/{nif} GET
- /api/empresas/nombre/{nombre} GET


## 8) Mejoras extra (opcional)
- Validaciones
- Estilos Bootstrap
- Búsqueda
- Pruebas
- Paginación
