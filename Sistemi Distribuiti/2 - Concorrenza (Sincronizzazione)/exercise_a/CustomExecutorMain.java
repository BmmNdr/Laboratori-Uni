import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CustomExecutorMain {

    // Svolge una prova di CustomExecutor con dei Runnable di esempio.
    public static void main(String[] args) {
        CustomExecutor executor = new CustomExecutor(5);

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

        // Dopo aver inviato 20 task, il thread main termina. Una volta che i task
        // sono stati eseguiti, i thread Worker rimangono comunque in attesa indefinita,
        // perché non c'è un meccanismo di chiusura per questo esercizio di prova.
        // Bisogna premere CRTL+C.
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

class CustomExecutor {
    // Lista dei task da eseguire.
    private final List<Runnable> tasks = new ArrayList<>();

    // Array che contiene i thread che eseguono i vari task.
    private final Thread[] threads;

    public CustomExecutor(int numThreads) {
        if (numThreads <= 0)
            throw new IllegalArgumentException("numThread must be positive");
        this.threads = new Worker[numThreads];

        // Costruttore dell'Executor: deve creare i thread, salvarli nella lista
        // e avviarli.
        for (int i = 0; i < numThreads; i++) {
            this.threads[i] = new Worker();
            this.threads[i].start();
        }
    }

    public void execute(Runnable task) {
        // Aggiunge il task alla lista e notifica ai thread che è stato aggiunto un task.

        synchronized(tasks){
            this.tasks.add(task);
            tasks.notify();
        }
    }

    private class Worker extends Thread {
        public void run() {
            while (true) {
                // Ottiene un task dalla lista ed esegue il task chiamando il metodo task.run().
                // Prosegue all'inifinito così.
                //
                // Attenzione: l'accesso alla lista deve essere protetto (mutua esclusione) e se la
                // lista è vuota il thread deve attendere un segnale. Quando si ottiene il task,
                // bisogna eseguirlo all'infuori della zona protetta altrimenti si mantiene per
                // troppo tempo il monitor della lista.
                Runnable task = null;

                synchronized(tasks){
                    while(tasks.isEmpty()){
                            try {
                                tasks.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }

                    task = tasks.removeLast();
                }

                task.run();
            }
        }
    }
}
