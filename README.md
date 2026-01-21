# CRUD MVC con Thymeleaf — RA3

cambiar en vez de coches -> empresas
createdb concesionario_db

## 1) Datos del alumno/a
- Entidad elegida (ej. Producto, Libro...):
- Coche

## 2) Repositorio (fork) y gestión de versiones
- Repositorio base: https://github.com/profeInformatica101/tareasSpringBoot
- Enlace a MI fork: https://github.com/AncheJeez/tareasSpringBoot
- Nº de commits realizados: (mínimo 5)

## 3) Arquitectura
Explica brevemente cómo has organizado:
- Controller:
   conapp/src/main/java/com/consesionarioapp/conapp/HomeController.java
- Service:
- Repository:
- Entity:

## 4) Base de datos elegida (marca una)
- PostgreSQL

## 5) Configuración de la base de datos
### 5.1 Dependencias añadidas
(Indica la dependencia del driver que has usado)

### 5.2 application.properties / application.yml
(Pega aquí tu configuración SIN contraseñas reales si es necesario)

### 5.3 Pasos para crear la BD (si aplica)
- MySQL: CREATE DATABASE ...
- PostgreSQL: createdb ...

## 6) Cómo ejecutar el proyecto
1. Requisitos (Java versión, Maven/Gradle, DB instalada si aplica)
2. Comando de arranque:
   - ./mvnw spring-boot:run   (o equivalente)
3. URL de acceso:
   - http://localhost:8080/...

## 7) Pantallas / Rutas MVC
- GET /entidad (listar)
- GET /entidad/nuevo (formulario alta)
- POST /entidad (crear)
- GET /entidad/{id}/editar (editar)
- POST /entidad/{id} (actualizar)
- POST /entidad/{id}/borrar (eliminar)


## 8) Mejoras extra (opcional)
- Validaciones
- Estilos Bootstrap
- Búsqueda
- Pruebas
- Paginación
