# Employee Management System

Spring MVC + Hibernate (JPA) + MySQL, with role-based access via Spring Security.

## Run
1. Install JDK 17+, Maven, MySQL.
2. Edit `src/main/resources/application.properties` (MySQL username/password).
3. `mvn spring-boot:run`  ->  open http://localhost:8080
   (the `ems_db` database and tables are created automatically)

## Demo logins (seeded on first run)
| Username | Password | Role  | Can do                  |
|----------|----------|-------|-------------------------|
| admin    | admin123 | ADMIN | view, add, edit, delete |
| hr       | hr123    | HR    | view, add, edit         |
| user     | user123  | USER  | view only               |

## Structure
- `model`      - JPA entities (Employee, AppUser, Role)
- `repository` - Spring Data JPA repositories (Hibernate underneath)
- `service`    - business logic + transactions
- `controller` - Spring MVC controllers
- `config`     - security rules, demo data seeder
- `templates`  - Thymeleaf views
