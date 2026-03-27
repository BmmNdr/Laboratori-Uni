import java.util.concurrent.ThreadLocalRandom;

public class NumberGenerator {
    // Buffer che contiene il numero generato (un solo elemento).
    private int number;

    // Flag che indica se il buffer è vuoto o meno.
    private boolean isEmpty = true;

    void generate() {
        var name = Thread.currentThread().getName();
        while (true) {
            try {
                // Addormenta il thread per simulare della computazione.
                Thread.sleep(1000);

                synchronized (this) {
                    while (!this.isEmpty) {
                        System.out.println(name + ": in attesa di poter produrre");
                        // Il buffer è pieno, attende che si svuoti.
                        this.wait();
                    }

                    // Riempe il buffer e aggiorna il flag.
                    this.number = ThreadLocalRandom.current().nextInt();
                    this.isEmpty = false;

                    // Notifica un thread a caso che il buffer è stato riempito.
                    //
                    // PROBLEMA: potrebbe svegliare un altro thread che esegue questo metodo e
                    // quindi anche quel thread rimarebbe bloccato, mentre un thread che esegue
                    // il get potrebbe non essere mai svegliarsi.
                    //
                    // Soluzione: usare notifyAll() per svegliare tutti i thread. Ovviamente
                    // ciò ha un costo di penalità.
                    this.notify();
                    //this.notifyAll();
                    System.out.println(name + ": " + this.number);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    void get() {
        var name = Thread.currentThread().getName();
        while (true) {
            try {
                Thread.sleep(1000);

                synchronized (this) {
                    while (this.isEmpty) {
                        System.out.println(name + ": in attesa di poter consumare");
                        this.wait();
                    }

                    this.isEmpty = true;
                    System.out.println(name + ": " + this.number);
                    this.notify();
                    //this.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public static void main(String[] args) {
        var generator = new NumberGenerator();

        new Thread(generator::get, "C1").start();
        new Thread(generator::generate, "P1").start();

        /*
        new Thread(generator::get, "C2").start();
        new Thread(generator::generate, "P2").start();
        new Thread(generator::generate, "P3").start();
        new Thread(generator::generate, "P4").start();
        new Thread(generator::generate, "P5").start();
        */
    }
}