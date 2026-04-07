# CS2031 - Week 02 Lab 02: JPA, Relaciones y Query Methods

## ¿Para qué sirve este repositorio?

Este repositorio es una API REST construida con **Spring Boot** que gestiona una Songs API usando **PostgreSQL** como base de datos y **Spring Data JPA** como capa de acceso a datos.

El objetivo principal de esta sesión de laboratorio es que los estudiantes comprendan tres conceptos fundamentales del desarrollo backend con Spring Boot:

1. **JPA (Java Persistence API)**: cómo mapear clases Java a tablas de base de datos usando anotaciones, eliminando la necesidad de escribir SQL manual para las operaciones CRUD.
2. **Relaciones entre entidades**: cómo modelar y persistir relaciones `@OneToOne`, `@OneToMany`, `@ManyToOne` y `@ManyToMany` entre entidades JPA.
3. **Query Methods**: cómo definir consultas personalizadas en los repositorios usando solo el nombre del método, sin escribir JPQL ni SQL.

---

## Cómo ejecutar el proyecto

### 1. Levantar la base de datos con Docker

El proyecto requiere una instancia de PostgreSQL corriendo localmente. Usa el `docker-compose.yml` incluido:

```bash
docker compose up -d
```

Esto levantará un contenedor PostgreSQL con la siguiente configuración:

| Parámetro | Valor |
|-----------|-------|
| Host | `localhost` |
| Puerto | `5432` |
| Base de datos | `jpa-db` |
| Usuario | `postgres` |
| Contraseña | `postgres` |

### 2. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

> **Nota:** La propiedad `spring.jpa.hibernate.ddl-auto=create-drop` hace que Hibernate cree el esquema automáticamente al iniciar y lo elimine al apagar. No necesitas crear tablas manualmente.

---

## Endpoints disponibles

El único recurso con controller completo es `Song`. Las demás entidades (`Album`, `Artist`, `Genre`) son parte de la actividad.

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/song/new` | Crea una nueva canción |
| `GET` | `/song` | Retorna todas las canciones |
| `GET` | `/song/{id}` | Retorna una canción por ID |
| `DELETE` | `/song?id={id}` | Elimina una canción por ID |

### Ejemplo de body para crear una canción

```json
{
  "title": "Bohemian Rhapsody",
  "releaseDate": "1975-10-31",
  "duration": 354
}
```

---

## Arquitectura y estructura del proyecto

El proyecto sigue la **arquitectura en capas** de Spring Boot donde cada capa tiene una responsabilidad específica.

```
src/main/java/org/lab/week02lab02/
├── Week02Lab02Application.java       # Punto de entrada de Spring Boot
├── controller/
│   └── SongController.java           # Capa de presentación: maneja peticiones HTTP
├── service/
│   └── SongService.java              # Capa de negocio: lógica de la aplicación
├── repository/
│   ├── SongRepository.java           # Repositorio JPA de Song
│   ├── ArtistRepository.java         # Repositorio JPA de Artist
│   └── GenreRepository.java          # Repositorio JPA de Genre
└── model/
    ├── Song.java                     # Entidad principal con relaciones JPA
    ├── Album.java                    # Entidad Album
    ├── Artist.java                   # Entidad Artist
    └── Genre.java                    # Entidad Genre
```

---

## Entidades y sus relaciones

### `Song` — Entidad principal

`Song.java` es la entidad central del dominio. Sus campos y relaciones son:

| Campo | Tipo | Anotación JPA | Descripción |
|-------|------|---------------|-------------|
| `id` | `long` | `@Id @GeneratedValue` | Clave primaria autogenerada |
| `title` | `String` | `@Column(nullable=false, length=100)` | Título de la canción |
| `releaseDate` | `Date` | — | Fecha de lanzamiento |
| `duration` | `Integer` | — | Duración en segundos |
| `genre` | `Genre` | `@ManyToOne` | Género musical de la canción |
| `artists` | `List<Artist>` | `@ManyToMany` | Artistas que interpretan la canción |
| `albums` | `List<Album>` | `@OneToMany` | Álbumes en los que aparece |

### Relaciones implementadas

#### `@ManyToOne` — Song → Genre

Una canción pertenece a un solo género, pero un género agrupa muchas canciones.

```java
@ManyToOne
private Genre genre;
```

#### `@ManyToMany` — Song ↔ Artist

Una canción puede tener múltiples artistas y un artista puede tener múltiples canciones. JPA genera automáticamente la tabla intermedia.

```java
@ManyToMany
private List<Artist> artists = new ArrayList<>();
```

#### `@OneToMany` — Song → Album

Una canción puede aparecer en múltiples álbumes.

```java
@OneToMany
private List<Album> albums = new ArrayList<>();
```

---

## JPA Repository

Todos los repositorios extienden `JpaRepository<T, ID>`, lo que provee métodos CRUD sin escribir ningún código adicional:

```java
public interface SongRepository extends JpaRepository<Song, Long> {
    // Sin @Repository: JPA lo registra automáticamente como bean
}
```

Los métodos disponibles por defecto incluyen `save()`, `findById()`, `findAll()`, `deleteById()`, `existsById()`, entre otros.

### Query Methods

`JpaRepository` permite definir consultas personalizadas solo nombrando el método correctamente. Spring Data JPA genera el SQL automáticamente en tiempo de compilación:

```java
public interface SongRepository extends JpaRepository<Song, Long> {
    // Busca canciones cuyo título contiene el keyword dado
    List<Song> findByTitleContaining(String keyword);

    // Busca canciones con duración mayor al valor dado
    List<Song> findByDurationGreaterThan(Integer duration);

    // Busca canciones cuyo título contiene keyword Y duración mayor a minDuration
    List<Song> findByTitleContainingAndDurationGreaterThan(String keyword, Integer minDuration);
}
```

Consulta la referencia completa de keywords en la [documentación oficial de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/repositories/query-keywords-reference.html).

---

## Actividad

La actividad de este laboratorio consiste en extender el proyecto con una de las entidades existentes (**excepto `Song`**).

1. **Elige una entidad**: `Album`, `Artist` o `Genre`.
2. **Crea su Service**: con la lógica de negocio necesaria, inyectando su repositorio por constructor.
3. **Crea su Controller**: anotado con `@RestController`, con al menos un endpoint que use un método por defecto de `JpaRepository` (`findAll`, `deleteById`, etc.).
4. **Define un Query Method** en el repositorio de la entidad elegida.
5. **Crea un endpoint** adicional en el controller que use ese Query Method.

### Ejemplo para `Artist`

```java
// ArtistRepository.java
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByNameContainingIgnoreCase(String name);
}

// ArtistController.java
@GetMapping("/search")
public ResponseEntity<List<Artist>> searchByName(@RequestParam String name) {
    return ResponseEntity.ok(artistService.findByName(name));
}
```

---

## Preguntas de reflexión

- ¿Cómo se especifica la clave primaria de una entidad en JPA?
- ¿Qué efecto tiene `CascadeType.ALL` en una relación JPA?
- ¿Qué diferencias existen entre `@Column` y `@JoinColumn`?

---

## Stack tecnológico

- **Java 21**
- **Spring Boot 3.5.5** (Web, Data JPA)
- **PostgreSQL 16** (via Docker)
- **Hibernate** (implementación JPA por defecto en Spring Boot)
- **Spring Data JPA** (repositorios y Query Methods)
