import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientBinary {
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
        
        try (var socket = new DatagramSocket(); var scanner = new Scanner(System.in)) {
            var targetFound = false;
            final var serverAddress = InetAddress.getByName("127.0.0.1");

            while (!targetFound) { // Ciclo infinito finché l'utente non invia CTRl+C.
                System.out.print("Inserisci il numero ipotizzato: ");
                var guess = scanner.nextInt();

                // Preparazione del datagramma di invio.
                var buf = new byte[5];
                buf[0] = 0x01;
                var intBuf = intToByteArray(guess);
                buf[1] = intBuf[0];
                buf[2] = intBuf[1];
                buf[3] = intBuf[2];
                buf[4] = intBuf[3];

                // Invio del datagramma.
                var datagram = new DatagramPacket(buf, buf.length, serverAddress, serverPort);
                socket.send(datagram);

                // Riceve il feedback dal server. Riusa lo stesso buffer di invio.
                datagram = new DatagramPacket(buf, buf.length);
                socket.receive(datagram);

                // L'header deve essere per forza 0x02.
                if (datagram.getData()[0] != 0x02) {
                    System.out.println("Header non riconosciuto o non valido!");
                    System.exit(0);
                }

                // Stampa il feedback in base al valore ricevuto.
                // Nota: i valori potrebbero essere codificati in una classe per renderlo più leggibile.
                switch (datagram.getData()[1]) {
                    case 0x00:
                        System.out.println("Indovinato!");
                        targetFound = true;
                        break;
                    case 0x01:
                        System.out.println("Troppo basso");
                        break;
                    case 0x02:
                        System.out.println("Troppo alto");
                        break;
                    default: // Anche il valore del feedback deve essere noto!
                        System.out.println("Feedback non riconosciuto!");
                        System.exit(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] intToByteArray(int value) {
        return new byte[] {
            (byte) (value >> 24),
            (byte) (value >> 16),
            (byte) (value >> 8),
            (byte) value
        };
    }
}