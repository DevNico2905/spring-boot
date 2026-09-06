# spring-boot

> Proyecto de práctica con **Spring Boot 3 (parent 4.1.1)**, **Spring Data JPA** y **PostgreSQL**, corriendo la base de datos en **Docker**. Hecho siguiendo el curso de YouTube: [Spring Boot Tutorial](https://www.youtube.com/watch?v=Cw0J6jYJtzw).
> Practice project with **Spring Boot 3 (parent 4.1.1)**, **Spring Data JPA**, and **PostgreSQL**, running the database in **Docker**. Built while following the YouTube course: [Spring Boot Tutorial](https://www.youtube.com/watch?v=Cw0J6jYJtzw).

---

## 🇪🇸 Español

### Descripción

API REST básica construida con Spring Boot que expone una lista de ingenieros de software. Incluye una entidad JPA (`SoftwareEngineer`), un controlador REST y una base de datos PostgreSQL levantada con Docker Compose.

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
    │   │   ├── Application.java                  # Clase principal + endpoint "Hello World"
    │   │   ├── SoftwareEngineer.java              # Entidad JPA
    │   │   └── SoftwareEngineerController.java    # Controlador REST
    │   └── resources/
    │       └── application.properties             # Configuración de la app y la BD
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

### Endpoints disponibles

| Método | Ruta                              | Descripción                              |
|--------|-----------------------------------|-------------------------------------------|
| GET    | `/`                                | Devuelve un saludo de prueba              |
| GET    | `/api/v1/software-engineers`      | Devuelve una lista de ingenieros de software (datos hardcodeados) |

### Créditos

Proyecto creado como ejercicio de aprendizaje siguiendo el curso de YouTube: [Spring Boot Tutorial](https://www.youtube.com/watch?v=Cw0J6jYJtzw).

---

## 🇬🇧 English

### Description

Basic REST API built with Spring Boot that exposes a list of software engineers. It includes a JPA entity (`SoftwareEngineer`), a REST controller, and a PostgreSQL database spun up with Docker Compose.

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
    │   │   ├── Application.java                  # Main class + "Hello World" endpoint
    │   │   ├── SoftwareEngineer.java              # JPA entity
    │   │   └── SoftwareEngineerController.java    # REST controller
    │   └── resources/
    │       └── application.properties             # App and DB configuration
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

### Available endpoints

| Method | Path                               | Description                                   |
|--------|-------------------------------------|------------------------------------------------|
| GET    | `/`                                  | Returns a test greeting                        |
| GET    | `/api/v1/software-engineers`        | Returns a list of software engineers (hardcoded data) |

### Credits

Project built as a learning exercise following the YouTube course: [Spring Boot Tutorial](https://www.youtube.com/watch?v=Cw0J6jYJtzw).
