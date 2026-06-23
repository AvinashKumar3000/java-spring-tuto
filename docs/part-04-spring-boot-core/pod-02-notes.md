# Spring Boot — Complete Notes

---

## 1. Auto Configuration

Spring Boot automatically configures your application based on the dependencies present on the classpath. It uses `@EnableAutoConfiguration` (included in `@SpringBootApplication`) to detect and apply sensible defaults.

**How it works:**

Spring Boot scans `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` (Spring Boot 3.x) or `spring.factories` (Spring Boot 2.x) to find all auto-configuration classes. Each class is conditionally applied using annotations like `@ConditionalOnClass`, `@ConditionalOnMissingBean`, etc.

```java
@SpringBootApplication  // = @Configuration + @EnableAutoConfiguration + @ComponentScan
public class MyApp {
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }
}
```

**Key conditional annotations:**

| Annotation | Activates when... |
|---|---|
| `@ConditionalOnClass` | A specific class is on the classpath |
| `@ConditionalOnMissingBean` | No bean of that type is already defined |
| `@ConditionalOnProperty` | A property is set to a specific value |
| `@ConditionalOnWebApplication` | The app is a web application |

**Debugging auto-configuration:**

```properties
# See what was auto-configured and why
debug=true
```

Or use the Actuator `/actuator/conditions` endpoint.

---

## 2. Starter Dependencies

Starters are curated dependency descriptors that bundle commonly used libraries for a specific concern. You add one starter instead of hunting for individual transitive dependencies.

**Naming convention:** `spring-boot-starter-*`

**Common starters:**

| Starter | Purpose |
|---|---|
| `spring-boot-starter-web` | REST APIs with Spring MVC + Tomcat |
| `spring-boot-starter-data-jpa` | JPA/Hibernate + database |
| `spring-boot-starter-security` | Spring Security |
| `spring-boot-starter-test` | JUnit 5, Mockito, AssertJ |
| `spring-boot-starter-actuator` | Production monitoring endpoints |
| `spring-boot-starter-validation` | Bean Validation (Hibernate Validator) |
| `spring-boot-starter-cache` | Caching abstraction |
| `spring-boot-starter-mail` | JavaMail integration |

**Maven example:**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

**Gradle example:**

```groovy
implementation 'org.springframework.boot:spring-boot-starter-web'
```

> You do **not** need to specify versions — the Spring Boot BOM (Bill of Materials) manages compatible versions for you.

---

## 3. application.properties / YAML

Spring Boot reads configuration from `src/main/resources/application.properties` or `application.yml`. Both formats are equivalent; YAML is preferred for hierarchical config.

**Properties format:**

```properties
server.port=8081
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=postgres
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**YAML format (equivalent):**

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: postgres
    password: secret
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

**Property loading order (highest to lowest priority):**

1. Command-line arguments (`--server.port=9090`)
2. `SPRING_APPLICATION_JSON` environment variable
3. OS environment variables
4. `application-{profile}.properties`
5. `application.properties`
6. `@PropertySource` annotations
7. Default values in `@Value`

**Binding custom properties to a class:**

```java
@ConfigurationProperties(prefix = "app.mail")
@Component
public class MailProperties {
    private String host;
    private int port;
    // getters and setters
}
```

```yaml
app:
  mail:
    host: smtp.example.com
    port: 587
```

---

## 4. Profiles

Profiles let you define environment-specific configuration (dev, test, prod) and activate them at runtime.

**Defining profile-specific config files:**

- `application-dev.yml`
- `application-prod.yml`
- `application-test.yml`

Spring Boot merges the active profile's file on top of `application.yml`.

**Activating a profile:**

```properties
# In application.properties
spring.profiles.active=dev
```

```bash
# As a JVM arg
java -jar app.jar --spring.profiles.active=prod

# As an environment variable
export SPRING_PROFILES_ACTIVE=prod
```

**Profile-specific beans:**

```java
@Configuration
@Profile("dev")
public class DevDataSourceConfig {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }
}
```

**Profile groups (Spring Boot 2.4+):**

```yaml
spring:
  profiles:
    group:
      production:
        - proddb
        - prodmq
```

---

## 5. Embedded Server

Spring Boot embeds a servlet container directly in the JAR, eliminating the need to deploy to an external application server.

**Default:** Apache Tomcat (via `spring-boot-starter-web`)

**Alternatives:** Jetty, Undertow

**Switching to Jetty:**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

**Common server configuration:**

```yaml
server:
  port: 8080
  servlet:
    context-path: /api
  tomcat:
    max-threads: 200
    connection-timeout: 5000
  ssl:
    key-store: classpath:keystore.jks
    key-store-password: secret
    enabled: true
```

**Programmatic customization:**

```java
@Bean
public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
    return factory -> factory.setPort(9090);
}
```

---

## 6. Actuator

Spring Boot Actuator exposes production-ready endpoints for monitoring and managing your application.

**Dependency:**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

**Key endpoints:**

| Endpoint | Description |
|---|---|
| `/actuator/health` | Application health status |
| `/actuator/info` | App info (from `application.properties`) |
| `/actuator/metrics` | Metrics (memory, CPU, requests) |
| `/actuator/env` | All environment properties |
| `/actuator/beans` | All Spring beans |
| `/actuator/mappings` | All `@RequestMapping` paths |
| `/actuator/conditions` | Auto-configuration conditions report |
| `/actuator/loggers` | View/change log levels at runtime |
| `/actuator/threaddump` | Current thread dump |
| `/actuator/httptrace` | Recent HTTP request/response traces |

**Enabling endpoints:**

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health, info, metrics, env
        # Or expose all: include: "*"
  endpoint:
    health:
      show-details: always
  server:
    port: 8081  # Separate port for management endpoints
```

**Custom health indicator:**

```java
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        boolean dbUp = checkDatabase();
        return dbUp ? Health.up().build() : Health.down().withDetail("reason", "DB unreachable").build();
    }
}
```

---

## 7. Logging

Spring Boot uses **SLF4J** as the logging facade and **Logback** as the default implementation. No extra configuration is required to get started.

**Log levels (in order of severity):** `TRACE` → `DEBUG` → `INFO` → `WARN` → `ERROR`

**Setting log levels:**

```yaml
logging:
  level:
    root: WARN
    com.example: DEBUG
    org.springframework.web: INFO
  file:
    name: logs/app.log
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
    file: "%d %p %c{1.} [%t] %m%n"
```

**Using the logger in code:**

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    public void placeOrder(Order order) {
        log.debug("Placing order: {}", order.getId());
        log.info("Order placed successfully for user: {}", order.getUserId());
        log.warn("Low inventory for product: {}", order.getProductId());
        log.error("Failed to process payment", exception);
    }
}
```

**Switching to Log4j2:**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

---

## 8. Devtools

`spring-boot-devtools` speeds up development by enabling automatic restart, live reload, and sensible dev-time defaults.

**Dependency:**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

**Features:**

**Automatic restart** — monitors classpath for changes. When a `.class` file changes (after compilation), the application context restarts. Uses two classloaders: one for third-party JARs (not reloaded) and one for your code (reloaded), making restarts much faster than a cold start.

**LiveReload** — triggers a browser refresh when static resources or templates change. Requires the LiveReload browser extension.

**Property defaults** — disables caching for template engines (Thymeleaf, FreeMarker) and enables debug logging for web requests in dev mode.

**Remote restart** — supports triggering restarts on a remote deployed instance (rarely used).

**Configuration:**

```yaml
spring:
  devtools:
    restart:
      enabled: true
      exclude: static/**,public/**  # Don't restart for static resource changes
    livereload:
      enabled: true
```

> Devtools is automatically disabled when running a packaged JAR (`java -jar`). It is development-only.

---

## 9. Exception Handling

Spring Boot provides multiple layers of exception handling for REST APIs and MVC applications.

**Default behavior:** Spring Boot returns a `/error` response with a JSON body for REST or a Whitelabel Error Page for browsers.

### @ExceptionHandler (Controller-level)

Handles exceptions thrown within a specific controller.

```java
@RestController
public class UserController {

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(UserNotFoundException ex) {
        return new ErrorResponse(404, ex.getMessage());
    }
}
```

### @ControllerAdvice / @RestControllerAdvice (Global)

Handles exceptions across all controllers.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(UserNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(", "));
        return new ErrorResponse(400, message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(Exception ex) {
        return new ErrorResponse(500, "An unexpected error occurred");
    }
}
```

### ResponseEntityExceptionHandler

Extend this to handle Spring MVC's built-in exceptions with full control over the response.

```java
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        // custom handling
        return new ResponseEntity<>(new ErrorResponse(400, "Validation failed"), HttpStatus.BAD_REQUEST);
    }
}
```

### ProblemDetail (Spring Boot 3.x / RFC 7807)

```yaml
spring:
  mvc:
    problemdetails:
      enabled: true
```

---

## 10. Validation

Bean Validation (JSR-380) via Hibernate Validator is the standard way to validate input.

**Dependency:**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

**Annotating a DTO:**

```java
public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Email(message = "Must be a valid email address")
    @NotBlank
    private String email;

    @NotNull
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 120)
    private Integer age;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    private String phone;
}
```

**Triggering validation in a controller:**

```java
@PostMapping("/users")
public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest request) {
    return ResponseEntity.ok(userService.create(request));
}
```

`@Valid` triggers validation. If it fails, `MethodArgumentNotValidException` is thrown (handle it in `@RestControllerAdvice`).

**Common constraint annotations:**

| Annotation | Applies to | Description |
|---|---|---|
| `@NotNull` | Any | Must not be null |
| `@NotBlank` | String | Not null and not empty/whitespace |
| `@NotEmpty` | String, Collection | Not null and not empty |
| `@Size(min, max)` | String, Collection | Size within range |
| `@Min` / `@Max` | Number | Minimum / maximum value |
| `@Email` | String | Valid email format |
| `@Pattern(regexp)` | String | Matches regex |
| `@Positive` | Number | Must be > 0 |
| `@Future` / `@Past` | Date | Must be in future/past |

**Custom validator:**

```java
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(email);
    }
}
```

---

## 11. Config Management

For production-grade configuration, Spring Boot provides tools to externalize, secure, and manage settings across environments.

### @ConfigurationProperties

The preferred way to bind structured config to a typed Java class.

```yaml
app:
  datasource:
    url: jdbc:postgresql://localhost/mydb
    pool-size: 10
    timeout: 5000
  feature-flags:
    new-checkout: true
```

```java
@ConfigurationProperties(prefix = "app.datasource")
@Validated
public class DataSourceProperties {

    @NotBlank
    private String url;

    @Positive
    private int poolSize;

    private Duration timeout;

    // getters and setters (or use a record in Spring Boot 3+)
}
```

Enable it in a config class or by annotating the main class:

```java
@EnableConfigurationProperties(DataSourceProperties.class)
```

### Externalized Configuration Sources

Ranked by priority (highest wins):

1. Command-line args: `--app.datasource.pool-size=20`
2. `SPRING_APPLICATION_JSON` environment variable
3. OS environment variables: `APP_DATASOURCE_URL=...`
4. `application-{profile}.yml`
5. `application.yml` (packaged inside the JAR)

### Encrypting Sensitive Properties

Use **Jasypt** or **Spring Cloud Config** with a Vault backend to avoid storing plain-text passwords.

```properties
# With Jasypt
spring.datasource.password=ENC(encryptedValueHere)
```

### Spring Cloud Config (Centralized Config)

For microservices, Spring Cloud Config Server serves config from a Git repository or Vault to all services.

```yaml
# bootstrap.yml (config client)
spring:
  application:
    name: order-service
  config:
    import: "configserver:http://localhost:8888"
```

### Using Environment Variables in Config

```yaml
spring:
  datasource:
    url: ${DATABASE_URL:jdbc:h2:mem:testdb}
    username: ${DB_USER:sa}
    password: ${DB_PASSWORD:}
```

The `${VAR:default}` syntax falls back to a default if the environment variable is not set.

### Config in Tests

```java
@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "app.feature-flags.new-checkout=false"
})
class OrderServiceTest {
    // ...
}
```

Or use `@ActiveProfiles("test")` with `application-test.yml`.

---

*Spring Boot Version Reference: Notes apply to Spring Boot 3.x (Java 17+) unless otherwise noted.*