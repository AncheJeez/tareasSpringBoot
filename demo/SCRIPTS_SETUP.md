# Ejecutar la Aplicación Spring Boot

## Resumen de cambios

La aplicación ahora:
- **No incluye datos hardcodeados** por defecto
- **DataLoader deshabilitado** en la configuración estándar
- Proporciona **scripts automatizados** para cargar datos de prueba cuando sea necesario

## Opciones de ejecución

### 1. **Modo Limpio (Sin datos de prueba)** - Recomendado para producción

**Con PowerShell (Windows):**
```powershell
.\run-clean.ps1
```

**Con Bash/Terminal:**
```bash
./mvnw.cmd spring-boot:run
```

o 

```bash
mvn spring-boot:run
```

### 2. **Modo Desarrollo (Con datos de prueba)** - Para testing

**Con PowerShell (Windows):**
```powershell
.\run-with-test-data.ps1
```

**Con Bash/Terminal:**
```bash
./run-with-test-data.sh
```

o manualmente:
```bash
./mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

## Datos de prueba cargados automáticamente

Cuando ejecutas con el perfil `dev`, se cargan automáticamente:

### Empresas
- Tech Solutions S.L. (Madrid)
- Digital Innovations S.A. (Barcelona)
- Global Consulting Group (Valencia)
- Ecomerce Solutions S.L. (Sevilla)
- Digital Marketing Agency (Zaragoza)
- CyberTech Labs S.A. (Bilbao)

### Usuarios de prueba
| Email | Usuario | Contraseña | Rol |
|-------|---------|------------|-----|
| admin@admin.es | admin | admin | ROLE_ADMIN |
| juana@tech.es | Juana | password1 | ROLE_ADMIN |
| maria@tech.es | Maria | password2 | ROLE_USER |
| carlos@digit.es | Carlos | password3 | ROLE_USER |
| anabel@digit.es | Anabel | password4 | ROLE_USER |

> Nota: Puedes iniciar sesión con **email o nombre de usuario**

## Acceso a la consola H2

Cuando la aplicación esté ejecutándose:
- URL: `http://localhost:8080/h2-console`
- Driver: `org.h2.Driver`
- URL JDBC: `jdbc:h2:file:./data/demo-db`
- Usuario: `sa`
- Contraseña: (dejar en blanco)

## Limpiar la base de datos

Para eliminar todos los datos y empezar desde cero:

```bash
# En PowerShell
Remove-Item -Path ".\data\demo-db.mv.db" -Force
Remove-Item -Path ".\data\demo-db.trace.db" -Force

# En Bash
rm -f ./data/demo-db.mv.db ./data/demo-db.trace.db
```

Luego ejecuta nuevamente la aplicación.
