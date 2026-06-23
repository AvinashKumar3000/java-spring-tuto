# tutorial

## What is ExecutorService?

The ExecutorService (part of the java.util.concurrent package) is a framework that simplifies executing tasks in asynchronous mode. Instead of manually creating, managing, and destroying Thread objects, ExecutorService provides a managed pool of threads to automatically handle task execution.

## Why Use It? (The Problem it Solves)

- Resource Management:
- Creating a new thread in Java is expensive. If you spawn 1,000 threads for 1,000 tasks, your system will likely crash due to memory exhaustion.

- Thread Reuse:
- It decouples task submission from task execution, allowing threads to be reused for multiple tasks.

- Queueing:
- If all threads are busy, incoming tasks are safely placed in a queue until a thread becomes available.

## Core Architecture & Workflow

- Task Submission: You submit a task (Runnable or Callable).
- Blocking Queue: If all threads in the pool are busy, the task waits in a queue.
- Thread Pool: Workers (threads) pull tasks from the queue and execute them.

### 01. Creating an ExecutorService

- The easiest way to create an ExecutorService is using the factory methods provided by the Executors class.

- Executors.newFixedThreadPool(int n)
- Executors.newCachedThreadPool()
- Executors.newSingleThreadExecutor()
- Executors.newScheduledThreadPool(int n)

### 02. Submitting Tasks

- You can submit two types of tasks to an ExecutorService:
  - `Runnable`: Represents a task that does not return a result.
  - `Callable<V>`: Represents a task that returns a result of type V and can throw checked exceptions.

#### Methods for Submission

- `execute(Runnable)`: Fires and forgets. You get no feedback or result back.
- `submit(Runnable)` or `submit(Callable<T>)`: Returns a `Future<T>` object, which can be used to track the status and retrieve the result of the task.
- `invokeAll(Collection)`: Executes a collection of tasks and returns a list of Future objects when all are complete.
- `invokeAny(Collection)`: Executes a collection of tasks and returns the result of the first successfully completed task, cancelling the rest.

### 03. Shutting Down the ExecutorService

- An ExecutorService will not destroy itself when it runs out of tasks.
- If you don't shut it down, the JVM will keep running because active threads are still alive.

- There are two primary ways to stop it:
- shutdown(): Initiates an orderly shutdown. It stops accepting new tasks, but allows currently submitted and queued tasks to finish executing.
- shutdownNow(): Attempts to stop all actively executing tasks immediately (via thread.interrupt()), skips waiting tasks in the queue, and returns a list of the un-started tasks.

## graceful shutdown example:

```java
executor.shutdown(); // Disable new tasks from being submitted
try {
    // Wait a while for existing tasks to terminate
    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        executor.shutdownNow(); // Cancel currently executing tasks
    }
} catch (InterruptedException ie) {
    executor.shutdownNow(); // Force cancel if current thread is interrupted
    Thread.currentThread().interrupt();
}
```
