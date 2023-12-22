package hw_5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Philosophers {
    private final AtomicInteger currentPhil = new AtomicInteger(0);

    public void eatAndThink() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 1; i < 6; i++) {
            final int threadId = i;
            executorService.submit(() -> {
                int meals = 3;
                while (meals > 0) {
                    if (currentPhil.get() != threadId) {
                        System.out.println("Philosopher " + threadId + " eats");
//                        System.out.println("Philosopher " + threadId + " thinks for 5 seconds");
                        currentPhil.set(threadId);
                        meals--;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt(); // set the interrupt flag
                            System.out.println("Thread was interrupted, Failed to complete operation");
                        }
                    }
                }
            });
        }





        executorService.shutdown();
    }

    public static void main(String[] args) {
        new Philosophers().eatAndThink();
    }
}
