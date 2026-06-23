# ApplicationContext

- `ApplicationContext` is the advanced Spring IoC container that creates, manages, and injects beans.
- It is a child of `BeanFactory`.

```bash
BeanFactory → basic
ApplicationContext → advanced
```

## Why it is used

- Creates beans
- Injects dependencies
- Manages lifecycle
- Supports events
- Supports AOP
- Internationalization (i18n)

```java
ApplicationContext context =
    new AnnotationConfigApplicationContext(AppConfig.class);

UserService userService =
    context.getBean(UserService.class);

context.getBean(UserService.class); // get by type
context.getBean("userService");     // get by name
```
