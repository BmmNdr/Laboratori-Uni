import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ServerBinary {

    public static void main(String[] args) {
        final var serverPort = 8080;

        // I datagrammi di invio/ricezione hanno la stessa luneghezza di 5 byte:
        //
        // Header: 1 byte
        // Contenuto: 4 byte
        //
        // Client->Server:
        //     Header: 1 byte a 0x01
        //     Contenuto: 4 byte (il numero da indovinare)
        //
        // Server->Client:
        //     Header: 1 byte a 0x02
        //     Contenuto: 1 byte (feedback codificato)
        //     Byte non usati: 3

        // Mappa che associa un client (numero porta) a un numero da indovinare. Quando un client
        // indovina si rimuove l'associazione nella mappa.
        final Map<Integer, Integer> targets = new HashMap<>();

        // Generatore pseudocasuale per i numeri da indovinare.
        final var random = new Random();
        
        try (var socket = new DatagramSocket(serverPort)) {
            System.out.println("Server in ascolto...");
            while (true) {
                // Buffer di ricezione del datagramm.
                var buf = new byte[5];
                var datagram = new DatagramPacket(buf, buf.length);
                socket.receive(datagram);
                System.out.printf("  Ricevuto datagramma da \"%s\"\n", datagram.getSocketAddress().toString());

                var clientAddress = datagram.getAddress(); // In questo caso sarà sempre "127.0.0.1".
                var clientPort = datagram.getPort();

                // Estrazione dei campi dal datagramma.
                var header = datagram.getData()[0];
                if (header != 0x01) {
                    System.out.println("Header non riconosciuto o non valido!");
                    continue;
                }
                var guess = byteArrayToInt(datagram.getData(), 1);

                // Se il client è nuovo: crea un nuovo numero da indovinare.
                if (!targets.containsKey(clientPort)) {
                    var target = random.nextInt(101); // Intervallo [0, 100].
                    targets.put(clientPort, target);
                }

                // Preparazione del feedback (logica del server).
                var target = targets.get(clientPort);
                System.out.printf("    Server: %d\tClient: %d\n", target, guess);
                byte feedback;
                if (guess == target) {
                    targets.remove(clientPort);
                    feedback = 0x00;
                } else if (guess < target) {
                    feedback = 0x01; 
                } else {
                    feedback = 0x02;
                }

                // Preparazione datagramma di invio. Si riusa il buffer di invio.
                buf[0] = 0x02;
                buf[1] = feedback;
                // Gli altri byte si possono ignorare perché non usati.

                // Invio del feedback.
                datagram = new DatagramPacket(buf, buf.length, clientAddress, clientPort);
                socket.send(datagram);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int byteArrayToInt(byte[] bytes, int offset) {
        return (bytes[offset] << 24) | (bytes[offset + 1] << 16) | (bytes[offset + 2] << 8) | (bytes[offset + 3]);
    }
}