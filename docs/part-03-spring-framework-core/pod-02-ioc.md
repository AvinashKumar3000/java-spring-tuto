# notes

## IoC (Inversion of Control)

- IoC means control of object creation and dependency management is given to Spring,
- instead of the programmer manually creating objects.\

- without ioc

```java
Engine e = new Engine();
Car c = new Car(e);
```

- with ioc
- spring creates Engine and injects them into Car.

## 2. Why it is used

- Reduces tight coupling
- Improves maintainability
- Easy testing (mock dependencies)
- Better scalability

## 3. Internal working

Spring IoC Container:

- Reads configuration (XML, Annotations, Java Config)
- Creates objects (beans)
- Resolves dependencies
- Injects dependencies
- Manages lifecycle

Main container:

- BeanFactory (basic)
- ApplicationContext (advanced)

### FLOW

```bash
Read config → Create bean → Inject dependency → Manage lifecycle
```

## Key classes / annotations

Important:

- ApplicationContext
- BeanFactory
- @Component
- @Service
- @Repository
- @Controller
- @Autowired
- @Bean
- @Configuration

```java
@Component
class Engine {}

@Component
class Car {
    @Autowired
    Engine engine;
}
```

```java
ApplicationContext context =
        new AnnotationConfigApplicationContext("com.app");

Car car = context.getBean(Car.class);
```

###

Quick Revision Points

- IoC = container controls object creation
- Spring container = IoC container
- DI is part of IoC
- Makes application loosely coupled
- Container manages full bean lifecycle

- @Autowired is DI mechanism.