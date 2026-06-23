import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorExample {
    public static void main(String[] args) {
        // 1. Create a thread pool with 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 2. Define a Callable task (returns a value)
        Callable<String> task = () -> {
            Thread.sleep(1000); // Simulate network/DB delay
            return "Task Result from " + Thread.currentThread().getName();
        };

        System.out.println("Submitting task...");
        
        // 3. Submit the task and get a Future handle
        Future<String> future = executor.submit(task);

        try {
            // 4. This blocks the main thread until the result is ready
            String result = future.get(); 
            System.out.println("Success: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5. CRITICAL: Always shut down the executor
            executor.shutdown();
        }
    }
}