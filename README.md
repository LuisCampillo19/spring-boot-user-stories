# Eventify

Plataforma de gestión de eventos construida con Spring Boot.

## Módulo 6 - Spring Boot

### M6.1S1 – Cimiento Arquitectónico (mayo 4–8)
- Spring MVC con repositorios en memoria
- Documentación Swagger/OpenAPI con springdoc
- Unit Testing con JUnit 5 + Mockito

### M6.1S2 – Persistencia Estratégica (mayo 11–15)
- Spring Data JPA + H2 (persistencia en archivo)
- CRUD completo: GET, POST, PUT, DELETE
- Paginación y ordenamiento con `Pageable`
- Pruebas de repositorio con `@DataJpaTest`

## Ejecutar

```bash
mvn spring-boot:run
```

## Documentación API

Swagger UI → http://localhost:8080/swagger-ui.html

## H2 Console (S2)

→ http://localhost:8080/h2-console  
JDBC URL: `jdbc:h2:file:./data/eventify-db`  
Usuario: `sa` | Contraseña: *(vacía)*

## Branching

```
main
 └── develop
      ├── feature/M6.1S1  (mayo 4–8)
      └── feature/M6.1S2  (mayo 11–15)
```

## Autor

[LuisCampillo19](https://github.com/LuisCampillo19)
