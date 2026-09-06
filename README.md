# spring-boot

> Proyecto de práctica con **Spring Boot 3 (parent 4.1.1)**, **Spring Data JPA** y **PostgreSQL**, corriendo la base de datos en **Docker**. Hecho siguiendo el curso de YouTube: [Spring Boot Tutorial](https://www.youtube.com/watch?v=Cw0J6jYJtzw).
> Practice project with **Spring Boot 3 (parent 4.1.1)**, **Spring Data JPA**, and **PostgreSQL**, running the database in **Docker**. Built while following the YouTube course: [Spring Boot Tutorial](https://www.youtube.com/watch?v=Cw0J6jYJtzw).

---

## 🇪🇸 Español

### Descripción

API REST construida con Spring Boot siguiendo una arquitectura en capas (**Controller → Service → Repository**) sobre una entidad JPA (`SoftwareEngineer`), persistida en PostgreSQL y levantada con Docker Compose. Permite listar, crear y consultar ingenieros de software por ID.

### Stack tecnológico

- **Java 21**
- **Spring Boot** (starter parent `4.1.1`)
  - `spring-boot-starter-webmvc`
  - `spring-boot-starter-data-jpa`
- **PostgreSQL** (driver `org.postgresql`)
- **Maven** (con wrapper `mvnw` / `mvnw.cmd`)
- **Docker / Docker Compose** para la base de datos
- **JUnit 5** para pruebas

### Estructura del proyecto

```
spring-boot/
├── docker-compose.yaml          # Contenedor de PostgreSQL
├── pom.xml                      # Dependencias y configuración de Maven
├── Request.http                 # Petición de ejemplo (REST Client)
└── src/
    ├── main/
    │   ├── java/org/devnico/
    │   │   ├── Application.java                     # Clase principal + endpoint "Hello World"
    │   │   ├── SoftwareEngineer.java                 # Entidad JPA
    │   │   ├── SoftwareEngineerController.java       # Controlador REST (capa web)
    │   │   ├── SoftwareEngineerService.java          # Lógica de negocio (capa de servicio)
    │   │   └── SoftwareEngineerRepository.java       # Acceso a datos (JpaRepository)
    │   └── resources/
    │       └── application.properties                # Configuración de la app y la BD
    └── test/
        └── java/org/devnico/ApplicationTests.java # Test de contexto de Spring
```

### Requisitos previos

- Java 21 (JDK)
- Docker y Docker Compose
- Maven (opcional, ya que el proyecto incluye el wrapper `mvnw`)

### Cómo ejecutar el proyecto

1. **Levantar la base de datos con Docker:**

   ```bash
   docker compose up -d
   ```

   Esto crea un contenedor `postgres-spring-boot` con:
   - Usuario: `amigoscode`
   - Contraseña: `password`
   - Puerto expuesto: `5332` (mapeado al `5432` interno del contenedor)

2. **Verificar que el contenedor esté corriendo:**

   ```bash
   docker compose ps
   ```

3. **(Opcional) Entrar al contenedor de la base de datos:**

   ```bash
   docker exec -it postgres-spring-boot bash
   psql -U amigoscode
   ```

4. **Ejecutar la aplicación Spring Boot:**

   ```bash
   ./mvnw spring-boot:run
   ```

   La app quedará disponible en `http://localhost:8080`.

5. **Probar los endpoints:**

   - `GET http://localhost:8080/` → `"Hello World Spring Boot!"`
   - `GET http://localhost:8080/api/v1/software-engineers` → lista de ingenieros de software (datos de ejemplo, aún no persistidos en la BD)

   También puedes usar el archivo `Request.http` incluido en el repo si tu editor soporta el plugin de REST Client (IntelliJ, VS Code, etc.).

### Configuración de la base de datos

La configuración vive en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5332/amigos
spring.datasource.username=amigoscode
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=create-drop
```

> ⚠️ **Nota:** el `docker-compose.yaml` no define explícitamente `POSTGRES_DB`, por lo que Postgres crea por defecto una base de datos con el mismo nombre que el usuario (`amigoscode`), mientras que `application.properties` apunta a una base llamada `amigos`. Si al levantar la app obtienes un error de "database does not exist", crea la base manualmente dentro del contenedor:
> ```bash
> docker exec -it postgres-spring-boot psql -U amigoscode -c "CREATE DATABASE amigos;"
> ```

`ddl-auto=create-drop` significa que Hibernate crea las tablas al iniciar la app y las elimina al detenerla — útil para desarrollo, no usar en producción.

### Arquitectura

El proyecto sigue una arquitectura en capas típica de Spring Boot:

```
SoftwareEngineerController  (capa web: recibe requests HTTP)
        │
        ▼
SoftwareEngineerService     (lógica de negocio)
        │
        ▼
SoftwareEngineerRepository  (JpaRepository → acceso a datos)
        │
        ▼
PostgreSQL
```

### Endpoints disponibles

| Método | Ruta                               | Body                                     | Descripción                                          |
|--------|--------------------------------------|-------------------------------------------|--------------------------------------------------------|
| GET    | `/`                                    | —                                           | Devuelve un saludo de prueba                            |
| GET    | `/api/v1/software-engineers`          | —                                           | Lista todos los ingenieros de software (desde la BD)   |
| GET    | `/api/v1/software-engineers/{id}`     | —                                           | Busca un ingeniero por ID                               |
| POST   | `/api/v1/software-engineers`          | `{ "name": "...", "techStack": "..." }`     | Crea un nuevo ingeniero de software                     |

Ejemplo de creación (ver también `Request.http`):

```http
POST http://localhost:8080/api/v1/software-engineers
Content-Type: application/json

{
  "name": "Nick",
  "techStack": "Java, Spring-Boot"
}
```

> Nota: `getById` lanza una `RuntimeException` genérica si el ID no existe (aún no hay un `@ExceptionHandler` que devuelva un 404 propiamente).

### Créditos

Proyecto creado como ejercicio de aprendizaje siguiendo el curso de YouTube: [Spring Boot Tutorial](https://www.youtube.com/watch?v=Cw0J6jYJtzw).

---

## 🇬🇧 English

### Description

REST API built with Spring Boot following a layered architecture (**Controller → Service → Repository**) on top of a JPA entity (`SoftwareEngineer`), persisted in PostgreSQL and spun up with Docker Compose. It supports listing, creating, and looking up software engineers by ID.

### Tech stack

- **Java 21**
- **Spring Boot** (starter parent `4.1.1`)
  - `spring-boot-starter-webmvc`
  - `spring-boot-starter-data-jpa`
- **PostgreSQL** (`org.postgresql` driver)
- **Maven** (with the `mvnw` / `mvnw.cmd` wrapper)
- **Docker / Docker Compose** for the database
- **JUnit 5** for testing

### Project structure

```
spring-boot/
├── docker-compose.yaml          # PostgreSQL container
├── pom.xml                      # Maven dependencies and config
├── Request.http                 # Sample request (REST Client)
└── src/
    ├── main/
    │   ├── java/org/devnico/
    │   │   ├── Application.java                     # Main class + "Hello World" endpoint
    │   │   ├── SoftwareEngineer.java                 # JPA entity
    │   │   ├── SoftwareEngineerController.java       # REST controller (web layer)
    │   │   ├── SoftwareEngineerService.java          # Business logic (service layer)
    │   │   └── SoftwareEngineerRepository.java       # Data access (JpaRepository)
    │   └── resources/
    │       └── application.properties                # App and DB configuration
    └── test/
        └── java/org/devnico/ApplicationTests.java # Spring context test
```

### Prerequisites

- Java 21 (JDK)
- Docker and Docker Compose
- Maven (optional, since the project ships with the `mvnw` wrapper)

### How to run

1. **Start the database with Docker:**

   ```bash
   docker compose up -d
   ```

   This creates a `postgres-spring-boot` container with:
   - User: `amigoscode`
   - Password: `password`
   - Exposed port: `5332` (mapped to the container's internal `5432`)

2. **Check that the container is running:**

   ```bash
   docker compose ps
   ```

3. **(Optional) Enter the database container:**

   ```bash
   docker exec -it postgres-spring-boot bash
   psql -U amigoscode
   ```

4. **Run the Spring Boot application:**

   ```bash
   ./mvnw spring-boot:run
   ```

   The app will be available at `http://localhost:8080`.

5. **Test the endpoints:**

   - `GET http://localhost:8080/` → `"Hello World Spring Boot!"`
   - `GET http://localhost:8080/api/v1/software-engineers` → list of software engineers (sample data, not yet persisted to the DB)

   You can also use the included `Request.http` file if your editor supports the REST Client plugin (IntelliJ, VS Code, etc.).

### Database configuration

Configuration lives in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5332/amigos
spring.datasource.username=amigoscode
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=create-drop
```

> ⚠️ **Note:** `docker-compose.yaml` doesn't explicitly set `POSTGRES_DB`, so Postgres defaults to creating a database with the same name as the user (`amigoscode`), while `application.properties` points to a database called `amigos`. If you get a "database does not exist" error on startup, create the database manually inside the container:
> ```bash
> docker exec -it postgres-spring-boot psql -U amigoscode -c "CREATE DATABASE amigos;"
> ```

`ddl-auto=create-drop` means Hibernate creates the tables on startup and drops them on shutdown — handy for development, not for production.

### Architecture

The project follows a typical Spring Boot layered architecture:

```
SoftwareEngineerController  (web layer: handles HTTP requests)
        │
        ▼
SoftwareEngineerService     (business logic)
        │
        ▼
SoftwareEngineerRepository  (JpaRepository → data access)
        │
        ▼
PostgreSQL
```

### Available endpoints

| Method | Path                                | Body                                       | Description                                       |
|--------|---------------------------------------|----------------------------------------------|-----------------------------------------------------|
| GET    | `/`                                     | —                                              | Returns a test greeting                             |
| GET    | `/api/v1/software-engineers`           | —                                              | Lists all software engineers (from the DB)          |
| GET    | `/api/v1/software-engineers/{id}`      | —                                              | Looks up a software engineer by ID                  |
| POST   | `/api/v1/software-engineers`           | `{ "name": "...", "techStack": "..." }`        | Creates a new software engineer                     |

Example request (also see `Request.http`):

```http
POST http://localhost:8080/api/v1/software-engineers
Content-Type: application/json

{
  "name": "Nick",
  "techStack": "Java, Spring-Boot"
}
```

> Note: `getById` throws a generic `RuntimeException` when the ID doesn't exist (there's no dedicated `@ExceptionHandler` yet to return a proper 404).

### Credits

Project built as a learning exercise following the YouTube course: [Spring Boot Tutorial](https://www.youtube.com/watch?v=Cw0J6jYJtzw).
