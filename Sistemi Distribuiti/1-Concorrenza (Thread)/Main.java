import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Esercizio C: Simulazione di un sistema di vendita biglietti
 * 
 * Simulare un sistema di vendita biglietti per un concerto con disponibilità limitata.
 * Versione CON sincronizzazione e tempo di transazione casuale.
 */
class Biglietteria {
    private int biglietti;
    
    /**
     * Costruisce un'istanza di biglietteria.
     * 
     * @param biglietti il numero totale di biglietti disponibili
     */
    public Biglietteria(int biglietti) {
        this.biglietti = biglietti;
    }
    
    /**
     * Vende un biglietto per conto di un rivenditore.
     * 
     * @param nomeRivenditore il nome del rivenditore che acquista
     * @return true se l'acquisto è riuscito, false se non ci sono biglietti
     */
    public synchronized boolean acquista(String nomeRivenditore) {
        //Se ci sono bilietti generare un tempo casuale di attesa usando ThreadLocalRandom,
        // quindi decrementare i biglietti e stampare il messaggio.

        if(this.biglietti > 0){
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            --this.biglietti;
            System.out.printf("[%s] acquista un biglietto! Rimasti: %d \n", nomeRivenditore, this.biglietti);
            return true;
        }

        //Se non ci sono biglietti, stampare il messaggio di esaurimento biglietti.
        System.out.println("BIGLIETTI ESAURITI");
        return false;
    }
}

/**
 * Classe Rivenditore che implementa Runnable.
 * 
 * Ogni rivenditore cerca di vendere più biglietti possibile.
 */
class Rivenditore implements Runnable {
    private Biglietteria biglietteria;
    private String nome;
    private int bigliettiAcquistati;
    /**
     * Costruisce un'istanza di rivenditore.
     * @param biglietteria l'istanza di Biglietteria condivisa
     * @param nome il nome del rivenditore
     */
    public Rivenditore(Biglietteria biglietteria, String nome) {
        this.biglietteria = biglietteria;
        this.nome = nome;

        this.bigliettiAcquistati = 0;
    }
    
    @Override
    public void run() {
        // Implementare il ciclo di vendita
        // Finché ci sono biglietti il metodo acquista() restituisce true, altrimenti false.
        while(biglietteria.acquista(this.nome)){
            try {
                ++this.bigliettiAcquistati;
                Thread.sleep(ThreadLocalRandom.current().nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("[%s] ha acquistato %d biglietti.\n", this.nome, this.bigliettiAcquistati);
    }
}

/**
 * Classe principale per avviare la simulazione.
 */
public class Main {
    final static int NUM_BIGLIETTI = 10;
    public static void main(String[] args) {
        int numRivenditori;
        Biglietteria bgl = new Biglietteria(NUM_BIGLIETTI);

        // Suggerimento: usare try-with-resources per la gestione automatica dello scanner.
        Scanner sc = new Scanner(System.in);
        try (sc) {
            System.out.printf("Inserire il numero di rivenditori: ");
            numRivenditori = sc.nextInt();
            System.out.println("Inizio vendita biglietti\n");

            Thread[] rivenditori = new Thread[numRivenditori];
            for (int i = 0; i < rivenditori.length; i++) {
                rivenditori[i] = new Thread(new Rivenditore(bgl, ("Rivenditore-" + i)));
                rivenditori[i].start();
            }
            
            System.out.printf("(%s rivenditori avviati)\n", numRivenditori);
        }
    }
}