import java.util.Random;

public class PrintRandom {
    public static void main(String[] args) {
        String name = Thread.currentThread().getName();
        Thread[] threads = new Thread[3];

        System.out.printf("Thread \"%s\": creazione e avvio di PrintThreadName\n", name);

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new PrintThreadName());
            threads[i].start();
        }

        try {
            for (Thread thread : threads)
                thread.join();
        } catch (InterruptedException e) {        }

        System.out.printf("Thread \"%s\": creazione e avvio di PrintThreadNumber\n", name);

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new PrintThreadNumber());
            threads[i].start();
        }

        try {
            for (Thread thread : threads)
                thread.join();
        } catch (InterruptedException e) {        }

        System.out.printf("Thread \"%s\": in terminazione\n", name);
    }
}

class PrintThreadName implements Runnable {
    private Random rng = new Random();
    private String threadName = "";

    public void run() {
        threadName = Thread.currentThread().getName();

        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(rng.nextInt(3000));
            } catch (InterruptedException e) {}

            System.out.printf("Thread \"%s\" opt. %d\n", threadName, i);
        }
    }
}

class PrintThreadNumber implements Runnable {
    private Random rng = new Random();
    private String threadName = "";

    public void run() {
        threadName = Thread.currentThread().getName();

        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(rng.nextInt(3000));
            } catch (InterruptedException e) {}

            int rngNum = rng.nextInt();
            System.out.printf("Thread \"%s\" opt. %d: %d\n", threadName, i, rngNum);
        }
    }
}