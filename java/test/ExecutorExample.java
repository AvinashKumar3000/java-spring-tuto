import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<String> task = () -> {
            Thread.sleep(2000);
            System.out.println("task completed...");
            return "the result";
        };

        Future<String> future = executor.submit(task);

        try {
            String ans = future.get();
            System.out.println("The answer is here. " + ans);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally{
            executor.shutdown();
        }
    }
}
