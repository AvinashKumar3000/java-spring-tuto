# notes

## Immutable objects

- An immutable object is an object whose state cannot be changed after creation.
- java string is immutable.

## why ?

- Thread-safe
  - No synchronization needed.
  - Multiple threads can use same object safely.

- Security ( Useful for )
  - passwords
  - tokens
  - URLs
  - If modifiable, attacker could alter.

- Caching
  - use full for hash code in hash map
  