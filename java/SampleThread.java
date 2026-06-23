
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("MyThread running");
    }
}

// Below method is preferred.
class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("MyTask is running");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("thread completed");
        }
    }
}
public class SampleThread {
    public static void main(String[] args) {
        System.out.println("main starts");
        MyThread mt1 = new MyThread();
        mt1.start();
        // ------------
        Thread thread = new Thread(new MyTask());
        thread.start();
        // -------------
        Thread th = new Thread(() -> {
            System.out.println("lambda thread running...");
        });
        th.start();
        System.out.println("main ends");

        /*
        NEW
        ↓
        RUNNABLE
        ↓
        RUNNING
        ↓
        WAITING / BLOCKED
        ↓
        TERMINATED
        */
    }    
}
