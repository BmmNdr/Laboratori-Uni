import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ServerText {
    public static void main(String[] args) {
        final var serverPort = 8080;

        // Mappa che associa un client (numero porta) a un numero da indovinare. Quando un client
        // indovina si rimuove l'associazione nella mappa.
        final Map<Integer, Integer> targets = new HashMap<>();

        // Generatore pseudocasuale per i numeri da indovinare.
        final var random = new Random();

        try (var socket = new DatagramSocket(serverPort)) {
            System.out.println("Server in ascolto...");
            while (true) {
                // Buffer per la ricezione del datagramma.
                var buf = new byte[100];
                var datagram = new DatagramPacket(buf, buf.length);
                socket.receive(datagram);
                System.out.printf("  Ricevuto datagramma da \"%s\"\n", datagram.getSocketAddress().toString());

                var clientAddress = datagram.getAddress(); // In questo caso sarà sempre "127.0.0.1".
                var clientPort = datagram.getPort();

                // Prima si converte il buffer di byte in una stringa, poi si converte la stringa
                // nel numero intero.
                //
                // Nota: se la stringa è malformata viene lanciata una eccezione e il server chiude.
                var guessStr = new String(datagram.getData(), 0, datagram.getLength());
                var guess = Integer.parseInt(guessStr);

                // Se il client è nuovo: crea un nuovo numero da indovinare.
                if (!targets.containsKey(clientPort)) {
                    var target = random.nextInt(101); // Intervallo [0, 100].
                    targets.put(clientPort, target);
                }

                // Preparazione del feedback (logica del server).
                var target = targets.get(clientPort);
                System.out.printf("    Server: %d\tClient: %d\n", target, guess);
                String feedback;
                if (guess == target) {
                    targets.remove(clientPort);
                    feedback = "Indovinato";
                } else if (guess < target) {
                    feedback = "Troppo basso"; 
                } else {
                    feedback = "Troppo alto";
                }

                // Invio del feedback.
                buf = feedback.getBytes();
                datagram = new DatagramPacket(buf, buf.length, clientAddress, clientPort);
                socket.send(datagram);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}