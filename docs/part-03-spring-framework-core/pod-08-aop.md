# aop ( Aspect-Oriented Programming )

- AOP is used to separate cross-cutting concerns from business logic.

- Cross-cutting concerns:
  - Logging
  - Security
  - Transactions
  - Caching

- > > without AOP
- Business code + Logging + Security mixed
- > > With AOP:
- Business code clean
- Aspect handles extra work
- why to use ?
- Avoid repeating same code.

eg: Instead of writing logging in every method:

```java
System.out.println("Method started");
// AOP once
```

TERMS

| Term       | Meaning                                      |
| ---------- | -------------------------------------------- |
| Aspect     | Class containing extra logic                 |
| Advice     | Action to execute                            |
| Join Point | Point where AOP can apply (method execution) |
| Point cut  | Rule to match methods                        |
| Target     | Actual business object                       |
| Proxy      | Wrapper created by Spring                    |

## types of advice

| Advice            | Runs when                     |
| ----------------- | ----------------------------- |
| `@Before`         | Before method                 |
| `@After`          | After method                  |
| `@AfterReturning` | After success                 |
| `@AfterThrowing`  | On exception                  |
| `@Around`         | Before + After (full control) |

```java
@Aspect
@Component
class LoggingAspect {

    @Before("execution(* com.app.service.*.*(..))")
    public void log() {
        System.out.println("Method called");
    }
}

userService.saveUser();
```
