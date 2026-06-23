# what is Thread ?

- A thread is the smallest unit of execution inside a process.

```bash
Chrome process
 ├── Thread 1 (UI)
 ├── Thread 2 (Network)
 └── Thread 3 (Rendering)

JAVA

Process = JVM
Thread = task inside JVM
```

- Every java program starts with Main thread
- To run multiple tasks simultaneously.
  - download file
  - payment process
  - send email

- advantage
  - better cpu usage
  - faster response
  - background tasks

## ways to create a thread

- method 1: extend a Thread
- method 2: Implement Runnable
- method 3: lambda

- Why Runnable preferred?
- Because Java supports single inheritance.

- What is synchronization?
- Makes shared resource access safe.

- What is deadlock?
- Two threads waiting on each other forever.

- What is race condition?
- Multiple threads changing shared data unexpectedly.s

- ExecutorService vs Thread?
- ExecutorService is better because:
  - Thread pooling
  - reusable
  - scalable

```bash
Thread = execution
Runnable = task
start = create thread
run = logic
sleep = pause
join = wait
synchronized = lock
ExecutorService = thread manager
```
