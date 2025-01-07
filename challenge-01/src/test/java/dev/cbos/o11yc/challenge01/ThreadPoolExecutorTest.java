package dev.cbos.o11yc.challenge01;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();
        System.out.println("### Start: " + start);

        int numberOfTasks = 40;

        final CountDownLatch latch = new CountDownLatch(numberOfTasks);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                4,  // Core pool size
                100, // Maximum pool size
                60L, // Keep alive time
                TimeUnit.MINUTES, // Keep alive time unit
                new LinkedBlockingQueue<Runnable>() // Work queue
        );

        for(int i=0; i<numberOfTasks; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                latch.countDown();
            });
        }

        // Shutdown the executor and wait will all are done
        executor.shutdown();
        try {
            latch.await();
            System.out.println("### End: " + start);
            System.out.println("### Elapsed time since start (in millisecons): " + Duration.between(start, LocalDateTime.now()).toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
