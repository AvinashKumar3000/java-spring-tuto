/*
    Here’s a classic Producer-Consumer example. It shows:

    wait() → thread waits until condition changes
    notify() → wakes one waiting thread
    notifyAll() → wakes all waiting threads
    Thread.sleep() → simulate delay
    synchronized shared object (Counter)
*/

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.*;

// ── Logging bootstrap ──────────────────────────────────────────────────────────
class LoggerFactory {
    private static final String FORMAT =
        "[%1$tF %1$tT.%1$tL] [%4$-7s] [%3$s] %5$s%n";

    static Logger get(Class<?> clazz) {
        Logger log = Logger.getLogger(clazz.getName());
        if (log.getHandlers().length == 0) {
            ConsoleHandler handler = new ConsoleHandler();
            handler.setFormatter(new SimpleFormatter() {
                @Override
                public synchronized String format(LogRecord r) {
                    return String.format(FORMAT,
                        r.getMillis(), r.getSourceClassName(),
                        r.getLoggerName(), r.getLevel(), r.getMessage());
                }
            });
            handler.setLevel(Level.ALL);
            log.addHandler(handler);
            log.setUseParentHandlers(false);
            log.setLevel(Level.ALL); // change to INFO in production
        }
        return log;
    }
}

// ── Shared counter ─────────────────────────────────────────────────────────────
class Counter {
    private static final Logger LOG = LoggerFactory.get(Counter.class);

    private int count = 2;
    private final int LIMIT = 5;

    public synchronized void increment() throws InterruptedException {
        String thread = Thread.currentThread().getName();

        LOG.fine(() -> thread + " acquired lock | count=" + count);

        while (count == LIMIT) {
            LOG.warning(() -> thread + " BLOCKED — counter full | count=" + count
                             + "/" + LIMIT + " | waiting for consumer");
            wait();
            LOG.fine(() -> thread + " resumed from wait | count=" + count);
        }

        count++;
        LOG.info(() -> thread + " PRODUCED → count=" + count + "/" + LIMIT);
        LOG.fine(() -> thread + " calling notifyAll()");
        notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        String thread = Thread.currentThread().getName();

        LOG.fine(() -> thread + " acquired lock | count=" + count);

        while (count == 0) {
            LOG.warning(() -> thread + " BLOCKED — counter empty | count=0"
                             + " | waiting for producer");
            wait();
            LOG.fine(() -> thread + " resumed from wait | count=" + count);
        }

        LOG.info(() -> thread + " CONSUMED ← count=" + (count - 1) + "/" + LIMIT
                      + " (was " + count + ")");
        count--;
        LOG.fine(() -> thread + " calling notifyAll()");
        notifyAll();
    }
}

// ── Main ───────────────────────────────────────────────────────────────────────
public class MultiThread {
    private static final Logger LOG = LoggerFactory.get(MultiThread.class);

    public static void main(String[] args) throws InterruptedException {
        LOG.info("Application start");

        Counter counter = new Counter();

        Thread producer = new Thread(() -> {
            LOG.info("Producer thread started | iterations=5");
            for (int i = 0; i < 5; i++) {
                try {
                    LOG.fine(() -> Thread.currentThread().getName()
                                  + " iteration=" + (Thread.currentThread().getState()));
                    counter.increment();
                    int sleep = ThreadLocalRandom.current().nextInt(500, 2000);
                    LOG.fine(() -> "Producer sleeping " + sleep + "ms");
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    LOG.severe("Producer interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            LOG.info("Producer thread done");
        }, "Producer");

        Thread consumer = new Thread(() -> {
            LOG.info("Consumer thread started | iterations=5");
            for (int i = 0; i < 5; i++) {
                try {
                    counter.decrement();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    LOG.severe("Consumer interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            LOG.info("Consumer thread done");
        }, "Consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        LOG.info("All threads complete | application exit");
    }
}