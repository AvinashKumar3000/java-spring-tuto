# notes

- Bean scope defines how many bean objects Spring creates and their lifecycle.

## TYPES OF BEAN SCOPE

- singleton ( default )
  - Only one object per Spring container.
  - Use when:
    - Stateless services
    - Shared objects

```java
@Component
@Scope("singleton")
class A {}

@Component
class A {}
```

- Prototype
  - New object every time requested.
  - Stateful objects

```java
@Component
@Scope("prototype")
class A {}

getBean(A.class); // new object
getBean(A.class); // new object
```

## important notes

- Request (Web)
  - One object per HTTP request.
  - `@Scope("request")`
- Session (Web)
  - One object per user session.
  - `@Scope("session")`
- Application (Web)
  - One object for entire web app.
  - `@Scope("application")`

- Singleton → one bean, shared
- Prototype → new bean every call

## which is used when ?

- Singleton (Most common — 95%)
- Used for:
  - Service classes
  - Repository classes
  - Utility classes
  - Config classes
- why ? Because these are stateless and can be shared.

- Prototype (Rare)
- Used when each usage needs fresh state.
- `ReportBuilder`
  - File builders
  - PDF generators
  - Temporary calculators
  - Dynamic query builders

| Scope           | Annotation                                    | Object Creation                      | Common Usage               |
| --------------- | --------------------------------------------- | ------------------------------------ | -------------------------- |
| **Singleton**   | `@Scope("singleton")`                         | One bean per container               | Shared stateless services  |
| **Prototype**   | `@Scope("prototype")`                         | New bean every request (`getBean()`) | Temporary/stateful objects |
| **Request**     | `@Scope("request")` / `@RequestScope`         | One bean per HTTP request            | Request-specific data      |
| **Session**     | `@Scope("session")` / `@SessionScope`         | One bean per user session            | User session data          |
| **Application** | `@Scope("application")` / `@ApplicationScope` | One bean for whole web app           | Global shared data         |

```bash
Singleton   → Entire app (shared)         -> UserService, PaymentService, Repository  
Prototype   → Every usage (new)           -> ReportBuilder, QueryBuilder, PDFGenerator
Request     → Every API call              -> RequestContext, JWTClaimsHolder
Session     → Every user login session    -> ShoppingCart, UserPreferences 
Application → Whole web application       -> AppConfigCache, MetricsCounter 
```

