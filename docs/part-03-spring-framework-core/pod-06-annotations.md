# notes

Spring Stereotype Annotations + Autowiring

| Annotation    | Purpose                            | Layer Used        | Special Feature        |
| ------------- | ---------------------------------- | ----------------- | ---------------------- |
| `@Component`  | Generic bean registration          | Any layer         | Base stereotype        |
| `@Service`    | Marks service/business logic class | Service layer     | Semantic clarity       |
| `@Repository` | Marks DAO/database class           | Persistence layer | Converts DB exceptions |
| `@Controller` | Handles HTTP requests              | Controller layer  | Used in MVC/web        |
| `@Autowired`  | Injects dependency automatically   | Any layer         | Performs DI            |

## NOTES

- DAO : Data Access Object.
- DTO : Data Transfer Object.

- `@Controller` : used with template engine
- `@RestController` : used in REST API

```bash
@Controller
   ↓
@Service
   ↓
@Repository
   ↓
Database
```
