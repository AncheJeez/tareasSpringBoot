# CRUD MVC con Thymeleaf — RA3

CAMBIOS SEGUNDA ENTREGA:
-se puede logear con usuario y email en vez de solo email
-ahora se puede logear un usuario sin necesariamente tener una empresa
-la contraseña se introduce dos veces y se valida mejor
-si se intenta acceder a una vista prohibida por role sale 403
-a la hora de editar usuario en vez de reutilizar vista de registro es una nueva vista
-nueva vista para cambio de contraseña de usuario
-vista personalizadas para errores 404 y 403
-ya no hay datos hardcodeados, para empezar con datos se utiliza el script en /demo, son scripts generados con ia codex
(run-clean.ps1, run-with-test-data.ps1, run-with-test-data.sh)
-no permite el borrado del usurario en sesión
-Mejoras ORM:
    -habilitado @EnableMethodSecurity y añadido accessDeniedPage("/error/403")
    -añadido controles (PreAuthorize)
    -validaciones de servidor, contraseña con formato especifico y role: ROLE_USER ROLE_ADMIN
    -validaciones @Email/@Size/@Pattern
    añadido excepciones ResourceNotFoundException y BadRequestException
    añadido GlobalExceptionHandler
-Implementado DTOs (UsuarioCreateDto, UsuarioUpdateDto, PasswordChangeDto)
-query-param -> RedirectAttributes.addFlashAttribute

-vista reporte mejorado, puedes ver las empresas y usuarios que pertenecen a cada ciudad.
-tabla de empresas ahora muestran codigo postal (antes no se mostraba)
-el campo de ciudad en crear/editar ahora hace fetch a los campos ciudades ya existentes o te deja escribir uno nuevo

para ejecutar:
> cd demo
> .\mvnw.cmd spring-boot:run
tiene que estar maven instalado
se puede borrar la carpeta /demo/data para borrar los datos y empezar de 0 otra vez

## 1) Datos del alumno/a
- Entidad elegida -> Empresa - Usuario

ANTIGUA TABLA NO ECHAR CUENTA A ESTO
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

IGNORAR ESTO, ES PREVIO CUANDO USABA POSTGRE
para activar postgre windows +r services.msc
localhost:8080
data.sql es el insert de prueba que introduzco en postgre pgadmin4

## 5) Configuración de la base de datos
### 5.1 Dependencias añadidas
(Indica la dependencia del driver que has usado)

### 5.2 application.properties / application.yml
he usado update no create, lo que significa que para que funcione se tiene que crear la base de datos manualmente
CREATE DATABASE empresa;

spring.jpa.hibernate.ddl-auto=update

### 5.3 Pasos para crear la BD (si aplica)
- Se crea automáticamente con unos datos de prueba
¡¡¡IMPORTANTE!!!
Hace falta tener en C:/Users/{Nobre de usuario}/ el fichero (test.mv.db)
y vacio

## 6) Cómo ejecutar el proyecto
1. Requisitos Java 25, Maven, H2
2. Comando de arranque:
      no uso comando, utilizo visual studio con la extensión de Springboot
3. URL de acceso:
   - http://localhost:8080/...

## 7) Pantallas / Rutas MVC

(PARA REGISTRARSE COMO ADMIN -> email admin@admin.es , contraseña admin)
- http://localhost:8080/
- http://localhost:8080/h2-console
- http://localhost:8080/login
- CREAR http://localhost:8080/usuarios/new
- EDITAR http://localhost:8080/empresas/{id_empresa}/edit
- CREAR http://localhost:8080/empresas/new
- EDITAR http://localhost:8080/empresas/{id_empresa}/edit

## 8) Mejoras extra (opcional)
- Validaciones
- Añadir Bootstrap
- Búsqueda
- Pruebas