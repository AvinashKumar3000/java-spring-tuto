# notes

Bean Lifecycle

- Bean lifecycle = the stages a bean goes through from
- creation → usage → destruction
- inside Spring.

```bash
Container     starts
↓
Bean          created (Instantiation)
↓
Dependencies  injected (Populate properties)
↓
@PostConstruct / init()
↓
Bean         ready to use
↓
Application     running
↓
@PreDestroy     / destroy()
↓
Bean             removed
```

## important phases

### 01. Instantiation

- Spring creates bean object.
- `new MyBean()`

### 02. Dependency Injection

- Spring injects required dependencies.
- `@Autowired Engine engine;`

### 03. Initialization

- Runs after dependency injection.
- Ways:
  - @PostConstruct
  - InitializingBean.afterPropertiesSet()
  - initMethod

```java
@PostConstruct
public void init() {
   System.out.println("Bean initialized");
}
```

### 04. Destruction

- Runs before bean removal.
- ways:
  - @PreDestroy
  - DisposableBean.destroy()
  - destroyMethod

Example

```java
@Component
class Demo {

    public Demo() {
        System.out.print("Constructor");
    }

    @PostConstruct
    public void init() {
        System.out.print("Init");
    }

    @PreDestroy
    public void destroy() {
        System.out.print("Destroy");
    }
}
```

```bash
Q1: What is bean lifecycle?
Process from creation to destruction.

Q2: When does @PostConstruct run?
After dependency injection.

Q3: When does @PreDestroy run?
Before bean destruction.

Q4: Does prototype bean call destroy method?
No. Spring doesn’t manage prototype destruction.

Q5: Bean lifecycle callback methods?

@PostConstruct
@PreDestroy
afterPropertiesSet()
destroy()
```

Singleton → full lifecycle managed
Prototype → destroy not managed
