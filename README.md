# API Empleados

API REST para gestionar los empleados de una empresa y sus roles, desarrollada con Spring Boot. Los datos se guardan en MySQL. Incluye un front-end simple (HTML, CSS y JavaScript) para usar la API desde el navegador.

Proyecto final del curso de Java. Parte del modelo y las validaciones de la preentrega 1.

## Tecnologías

- Java 17
- Spring Boot 4.1.2 (Spring Web MVC)
- Spring Data JPA / Hibernate
- MySQL
- Maven (incluye Maven Wrapper)
- Front-end: HTML, CSS y JavaScript (`fetch`)

## Requisitos previos

- JDK 17 o superior
- MySQL en ejecución en `localhost:3306`, usuario `root` y contraseña `root`
- VS Code con la extensión **Live Server** (para el front)
- Postman u otro cliente HTTP (opcional, para probar la API directamente)

## Estructura del proyecto

```
proyecto-final-java/
├── db/
│   └── schema.sql                      # Crea la base, las tablas y carga los roles
├── front/
│   ├── index.html
│   ├── styles.css
│   └── script.js
└── src/main/java/com/proyectofinal/api_empleados/
    ├── ApiEmpleadosApplication.java    # Clase principal
    ├── config/
    │   └── CorsConfig.java             # Permite llamadas desde el front (Live Server)
    ├── controller/
    │   ├── EmpleadoController.java     # Endpoints /empleados
    │   └── RolController.java          # Endpoint /roles
    ├── service/
    │   ├── EmpleadoService.java        # Lógica de negocio
    │   └── RolService.java
    ├── repository/
    │   ├── EmpleadoRepository.java     # Acceso a datos + consultas
    │   └── RolRepository.java
    ├── model/
    │   ├── Empleado.java               # Entidad Empleado (@ManyToOne con Rol)
    │   └── Rol.java                    # Entidad Rol
    ├── dto/
    │   ├── EmpleadoConRol.java         # Vista resumida de empleado
    │   └── CantidadPorRol.java         # Resultado de un GROUP BY
    ├── util/
    │   └── Validador.java              # Validaciones de legajo, nombre, apellido y CUIL
    └── exception/
        ├── GlobalExceptionHandler.java # Manejo centralizado de errores
        ├── EmpleadoNoEncontradoException.java
        ├── EmpleadoDuplicadoException.java
        └── RolNoEncontradoException.java
```

La aplicación sigue una arquitectura en capas: **controller → service → repository → base de datos**.

## Base de datos

La conexión se define en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/empleados_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=validate
```

Como `ddl-auto` está en `validate`, Hibernate **no crea las tablas**: solo verifica que existan y coincidan con las entidades. Antes de iniciar la aplicación hay que ejecutar el script `db/schema.sql` (por ejemplo, desde MySQL Workbench). El script:

- crea la base `empleados_db`,
- crea las tablas `rol` y `empleado` (`legajo` y `cuil` son únicos),
- carga los roles: Desarrollador, Analista, RRHH y Gerente.

Si el usuario o la contraseña de MySQL son otros, se modifican en `application.properties`.

## Cómo ejecutar

### API

Desde la carpeta del proyecto (`proyecto-final-java`):

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

### Front-end

Con la API en ejecución, abrir `front/index.html` con **Live Server** (clic derecho → *Open with Live Server*). Se abre en `http://127.0.0.1:5500`.

> No abrir el archivo con doble clic (`file://`): el navegador bloquea las llamadas a la API por CORS. La API solo acepta los orígenes `http://127.0.0.1:5500` y `http://localhost:5500`.

Desde el front se puede:

- ver los empleados en tarjetas,
- crear y editar empleados (el rol se elige de una lista cargada desde la API),
- eliminar empleados (con confirmación),
- buscar por apellido y filtrar por rol.

## Endpoints

### Empleados

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/empleados` | Lista todos los empleados | 200 |
| GET | `/empleados/{id}` | Obtiene un empleado | 200, 404 |
| GET | `/empleados?apellido={texto}` | Busca por apellido (contiene, sin distinguir mayúsculas) | 200 |
| GET | `/empleados/rol/{rol}` | Lista los empleados de un rol (por nombre) | 200 |
| GET | `/empleados/con-rol` | Vista resumida: nombre completo, email y rol como texto | 200 |
| GET | `/empleados/cantidad-por-rol` | Cantidad de empleados por rol | 200 |
| POST | `/empleados` | Crea un empleado | 201, 400, 409 |
| PUT | `/empleados/{id}` | Actualiza un empleado | 200, 400, 404, 409 |
| DELETE | `/empleados/{id}` | Elimina un empleado | 204, 404 |

### Roles

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/roles` | Lista los roles | 200 |

Los roles se cargan desde `db/schema.sql`; la API no los crea ni los modifica.

### Códigos de error

| Código | Cuándo |
|---|---|
| 400 | Datos inválidos (legajo ≤ 0, nombre o apellido vacíos, CUIL sin 11 dígitos) o rol inexistente |
| 404 | No existe un empleado con ese id |
| 409 | Ya existe otro empleado con ese legajo o CUIL |

Todos los errores se responden con el mismo formato:

```json
{ "error": "El CUIL debe tener 11 dígitos numéricos." }
```

## Ejemplos

### Crear un empleado

`POST /empleados`

```json
{
  "legajo": 1001,
  "nombre": "Ana",
  "apellido": "Gomez",
  "cuil": "27123456789",
  "rol": { "id": 2 }
}
```

- El rol se envía como objeto con su id. Es opcional: sin `rol` (o con `null`), el empleado queda sin rol.
- El `id` y el `email` no se envían: el id lo asigna la base y el email se genera a partir del nombre y el apellido.

Respuesta `201 Created`:

```json
{
  "id": 1,
  "legajo": 1001,
  "nombre": "Ana",
  "apellido": "Gomez",
  "cuil": "27123456789",
  "email": "agomez@mail.com",
  "nombreCompleto": "Gomez, Ana",
  "rol": { "id": 2, "nombre": "Analista" }
}
```

### Actualizar un empleado

`PUT /empleados/1` con el mismo formato que el POST. Un empleado puede conservar su propio legajo y CUIL; si se usan los de otro empleado, la respuesta es `409`.

### Vista resumida

`GET /empleados/con-rol`

```json
[
  { "id": 1, "legajo": 1001, "nombreCompleto": "Gomez, Ana", "email": "agomez@mail.com", "rol": "Analista" }
]
```

### Cantidad por rol

`GET /empleados/cantidad-por-rol`

```json
[
  { "rol": "Analista", "cantidad": 1 },
  { "rol": "Desarrollador", "cantidad": 3 }
]
```

## Autor

[agusfmartinez](https://github.com/agusfmartinez)
