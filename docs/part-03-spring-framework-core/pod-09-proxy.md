# notes

- Proxy Pattern is a design pattern where a proxy object acts as a middle layer between client and real object.
- Client → Proxy → Real Object
- Proxy controls access.
- why used
  - Logging
  - Security
  - Lazy loading
  - Caching
  - Transactions

## In Spring AOP

- Spring creates proxy objects to apply:
  - @Transactional
  - Logging
  - Security
