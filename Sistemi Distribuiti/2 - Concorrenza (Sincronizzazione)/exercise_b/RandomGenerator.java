import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {
    public static void main(String[] args) {
        var buffer = new Buffer(5);

        new Thread(new Producer(buffer, "integer"), "P-INT").start();
        new Thread(new Producer(buffer, "double"), "P-DBL").start();
        new Thread(new Producer(buffer, "string"), "P-STR").start();
        new Thread(new Consumer(buffer), "C1").start();
        new Thread(new Consumer(buffer), "C2").start();

        System.out.println("Thread main in terminazione");
    }
}

class Buffer {
    private final Queue<String> buffer;
    private int capacity;

    public Buffer(int capacity) {
        this.buffer = new LinkedList<String>();
        this.capacity = capacity;
    }

    public synchronized void add(String item) {
        while(this.buffer.size() >= this.capacity){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.buffer.add(item);
        this.notifyAll();
    }

    public synchronized String get() {
        while(this.buffer.isEmpty()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String item = this.buffer.remove();
        this.notifyAll();
        return item;
    }
}

class Producer implements Runnable {
    private final Buffer buffer;
    private final String type;

    public Producer(Buffer buffer, String type) {
        this.buffer = buffer;
        this.type = type;
    }

    public void run() {
        var name = Thread.currentThread().getName();

        try {
            while (true) {
                var item = generateItem();
                System.out.printf("%s\tGenerato \"%s\"\n", name, item);

                // Aggiunge l'elemento al buffer. Il il thread potrebbe attendere se il
                // buffer è pieno affinché si svuoti.
                buffer.add(item);

                // Prima di proseguire simula un ritardo casuale.
                Thread.sleep(ThreadLocalRandom.current().nextInt(5000, 6000));
            }
        } catch (InterruptedException e) {
            // Termina il thread dopo aver stampato l'eccezione.
            e.printStackTrace();
        }
    }

    private String generateItem() {
        switch (type) {
            case "integer":
                return Integer.toString(ThreadLocalRandom.current().nextInt());

            case "double":
                return Double.toString(ThreadLocalRandom.current().nextDouble());

            case "string":
                // Caratteri ammessi nella scelta a caso.
                final var characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

                // Poiché le stringhe sono immutabili, devo costruire un array di caratteri.
                var chars = new char[10];
                for (var i = 0; i < chars.length; i++) {
                    chars[i] = characters.charAt(ThreadLocalRandom.current().nextInt(characters.length()));
                }

                // L'array di caratteri viene convertito in stringa.
                return new String(chars);
            default:
                throw new IllegalArgumentException("Tipo non riconosciuto!");
        }
    }
}

class Consumer implements Runnable {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        var name = Thread.currentThread().getName();

        try {
            while (true) {
                var item = buffer.get();

                System.out.printf("%s\tConsumato \"%s\"\n", name, item);

                // Prima di proseguire simula un ritardo casuale.
                Thread.sleep(ThreadLocalRandom.current().nextInt(5000, 6000));
            }
        } catch (InterruptedException e) {
            // Termina il thread dopo aver stampato l'eccezione.
            e.printStackTrace();
        }
    }
}