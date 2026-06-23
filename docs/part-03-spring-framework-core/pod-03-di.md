# notes

- dependency injection
- Dependency Injection is a design pattern where Spring injects required dependencies into an object instead of the object creating them itself.

## Why it is used

- Reduces tight coupling
- Easier unit testing
- Better code reusability
- Easier maintenance

## Types of DI

- constructor injections ( recommended )

```java
@Component
class Car {
    private final Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }
}
```

- Best because:
- Immutable
- Mandatory dependency
- Better testing

- setter injection ( Used for optional dependency. )

```java
@Component
class Car {
    private Engine engine;

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
```

- Field Injection ( Hard to test. ) ( not-recommended )

```java
@Component
class Car {
    @Autowired
    private Engine engine;
}
```

## Key annotations / classes

```bash
        @Autowired
        @Qualifier
        @Primary
        @Component
        @Bean
        ApplicationContext
```

## QNs

- What if multiple beans of same type exist?
- `@Qualifier("beanName")`

```java
@Autowired
@Qualifier("petrolEngine")
Engine engine;
```

## What is @Primary?

- Default bean among multiple candidates.

```java
@Primary
@Component
class PetrolEngine implements Engine {}
```
