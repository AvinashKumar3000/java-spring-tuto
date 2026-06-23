# notes

- CompletableFuture
- CompletableFuture (introduced in Java 8) is used for asynchronous programming.

## It helps

- Run tasks in background
- Chain tasks
- Combine multiple tasks
- Handle exceptions
- Avoid callback hell

## Think

- Future = get result later
- CompletableFuture = get result later + continue processing

### before complete Future

```java
ExecutorService ex = Executors.newFixedThreadPool(2);

Future<String> future = ex.submit(() -> {
    Thread.sleep(2000);
    return "Hello";
});

System.out.println(future.get()); // blocking
```

- get() blocks
- cannot chain
- no easy exception handling

## Creating CompletableFuture

- runAsync() → no return

```java
CompletableFuture<Void> future =
        CompletableFuture.runAsync(() -> {
            System.out.println("Running...");
        });
```

- sending email
- logging
- notifications

## supplyAsync() → returns value

```java
CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> {
            return "Hello";
        });

System.out.println(future.get());
```

## thenApply() (Transform result)

```java
CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> "java")
                .thenApply(data -> data.toUpperCase());

System.out.println(future.get());
```

## then accept

```java
CompletableFuture.supplyAsync(() -> "Hello")
        .thenAccept(System.out::println);
```

## thenRun() (No input, no output)

```java
CompletableFuture.supplyAsync(() -> "Task")
        .thenRun(() -> System.out.println("Done"));
```

## thenCompose() (Dependent tasks)

- Use when second task depends on first.

```java
CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> "User")
                .thenCompose(user ->
                        CompletableFuture.supplyAsync(() -> user + " Orders"));

System.out.println(future.get());
```

## then combine

```java
CompletableFuture<String> f1 =
        CompletableFuture.supplyAsync(() -> "Hello");

CompletableFuture<String> f2 =
        CompletableFuture.supplyAsync(() -> " World");

CompletableFuture<String> result =
        f1.thenCombine(f2, (a, b) -> a + b);

System.out.println(result.get());
```

## allOf() (Wait all)

```java
CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "A");
CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "B");

CompletableFuture<Void> all =
        CompletableFuture.allOf(f1, f2);

all.join();

System.out.println(f1.join());
System.out.println(f2.join());
```

Example:

- user data
- payment data
- order data

## anyOf()

- return first response

```java
CompletableFuture<Object> any =
        CompletableFuture.anyOf(f1, f2);

System.out.println(any.get());
```

## exception

```java
CompletableFuture<Integer> future =
        CompletableFuture.supplyAsync(() -> 10 / 0)
                .exceptionally(ex -> 0);

System.out.println(future.get());
```

## handle()

```java
CompletableFuture<Integer> future =
        CompletableFuture.supplyAsync(() -> 10 / 0)
                .handle((res, ex) -> {
                    if (ex != null) return 0;
                    return res;
                });
```

### When to use?

- multiple API calls
- parallel DB calls
- async notifications
- file processing
- microservices

### TRICKS

```bash
    supplyAsync      -> produce
    thenApply        -> modify
    thenAccept       -> consume
    thenRun          -> just run
    thenCompose      -> dependent
    thenCombine      -> independent
    allOf            -> all
    anyOf            -> first
    exceptionally    -> fallback
```

