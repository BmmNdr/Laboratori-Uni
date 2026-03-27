import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class FixedThreadPoolMain {
    // Svolge una prova di CustomExecutor con dei Runnable di esempio.
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Prima esegue 10 task.
        for (int id = 0; id < 10; id++) {
            executor.execute(new TestRunnable(id));
        }
        System.out.println("Inviati i primi 10 task!");

        // Simula del lavoro nel thread main per far svuotare la coda dei task
        // in CustomExecutor.
        try {
            // Simula del lavoro con la CPU.
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Esegue poi altri 10 task.
        for (int id = 0; id < 10; id++) {
            executor.execute(new TestRunnable(id));
        }
        System.out.println("Inviati altri 10 task!");

        System.out.println("Thread main terminato!");

        executor.shutdown();
    }
}

class TestRunnable implements Runnable {
    private final int id;

    public TestRunnable(int id) {
        this.id = id;
    }

    // Esempio di run() che simula del lavoro con la CPU e stampa un messaggio di log
    // con l'ID del task e il nome del tread che lo esegue.
    public void run() {
        var name = Thread.currentThread().getName();
        System.out.printf("Task %d in esecuzione dal thread %s\n", this.id, name);

        try {
            // Simula del lavoro con la CPU tra 1,5 e 3,5 secondi.
            Thread.sleep(ThreadLocalRandom.current().nextInt(1500, 3500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}