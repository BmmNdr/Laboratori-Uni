import java.util.Random;

public class Corsa {
    public static void main(String[] args) {
        // Nodi predefiniti per i cavalli.
        var nomi = new String[]{
            "Furia", "Spirit", "Zorra", "Freccia", "Tempesta",
            "Fulmine", "Saetta", "Galoppo", "Vento", "Stella"
        };

        if(args.length == 0){
            System.out.println("Argomenti Mancanti");
            return;
        }

        int numCavalli = Integer.parseInt(args[0]);
        if(numCavalli <= 0){
            System.out.println("Numero Cavalli Errato");
            return;
        }
        
        Thread[] cavalli = new Thread[numCavalli];

        for (int i = 0; i < cavalli.length; i++) {
            String nome = (i < nomi.length) ? nomi[i] : ("Cavallo-" + i);
            
            cavalli[i] = new Thread(new Cavallo(nome));
            cavalli[i].start();
        }
    }
}

class Cavallo implements Runnable {
    final int DIST_TOTALE = 100;

    private String nome;
    private Random rng;
    private int distanzaPercorsa;

    /**
     * Crea un nuovo cavallo con un nome.
     * @param nome il nome del cavallo
     */
    public Cavallo(String nome) {
        this.nome = nome;
        this.distanzaPercorsa = 0;
        this.rng = new Random();
    }
    
    @Override
    public void run() {

        do {
            int rngWait = rng.nextInt(10,3000);
            try {
                Thread.sleep(rngWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int rngDist = rng.nextInt(10);
            this.distanzaPercorsa += rngDist;

            if(this.distanzaPercorsa > DIST_TOTALE)
                this.distanzaPercorsa = DIST_TOTALE;

            System.out.printf("[%s] ha percorso %d metri.\n", this.nome, this.distanzaPercorsa);

        } while (this.distanzaPercorsa < DIST_TOTALE);

        System.out.printf("[%s] ARRIVATO!\n", this.nome);
    }
}