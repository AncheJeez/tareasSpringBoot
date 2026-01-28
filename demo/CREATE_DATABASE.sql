-- Script para crear la base de datos empresadb en PostgreSQL
-- Ejecutar como usuario postgres

-- Crear la base de datos
CREATE DATABASE empresadb
    WITH
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    TEMPLATE = template0;

-- Conectarse a la base de datos
\c empresadb

-- Las tablas se crearán automáticamente con Hibernate
-- cuando ejecutes la aplicación Spring Boot
