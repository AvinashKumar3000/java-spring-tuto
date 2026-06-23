
import java.util.concurrent.ThreadLocalRandom;

class Counter {
    private int count = 0;
    final private int LIMIT = 5;

    synchronized public void increment() throws InterruptedException {
        System.out.println("👆 incrementing...");
        if (count == LIMIT) {
            System.out.println("⌚ waiting... to decrement...");
            wait();
        }
        count++;
        System.out.println("🪵 Producer incremented : 🪵 " + count);
        notifyAll();
    }

    synchronized public void decrement() throws InterruptedException {
        System.out.println("👆 decrementing...");
        if (count == 0) {
            System.out.println("⌚ waiting... to increment...");
            wait();
        }
        System.out.println("🪵 consumer decremented : 🪵 " + count);
        count--;
    }
}

public class TestThread {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    int sleepTime = ThreadLocalRandom.current().nextInt(500, 1000);
                    try {
                        Thread.sleep(sleepTime);
                        counter.increment();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                int sleepTime = ThreadLocalRandom.current().nextInt(500, 1000);
                try {
                    Thread.sleep(sleepTime);
                    counter.decrement();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
