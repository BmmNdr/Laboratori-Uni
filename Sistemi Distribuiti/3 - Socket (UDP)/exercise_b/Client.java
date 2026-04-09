import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(); Scanner scanner = new Scanner(System.in)) {
            final var port = 8080;
            final var serverAddress = InetAddress.getByName("127.0.0.1");

            while (true) {
                // Creo il buffer da inviare: 1 byte comando + 4 byte indice
                var buf = new byte[5];

                System.out.print("Inserisci comando (1: Leggi contatore, 2: Incrementa contatore, 0: Esci): ");
                var command = scanner.nextInt();

                if (command == 0)
                    break;

                // Primo byte comando
                buf[0] = (byte) command;


                System.out.print("Inserisci l'indice del contatore: ");
                var index = scanner.nextInt();
                var indexBuff = intToByteArray(index); //transformo l'intero in un array di 4 byte
                for (int i = 1; i <= 4; i++) {
                    buf[i] = indexBuff[i - 1];
                }

                var datagram = new DatagramPacket(buf, buf.length, serverAddress, port);
                socket.send(datagram);
                System.out.println("Datagram Inviato!");

                // con il comando di lettura devo attendere una risposta
                if (command == 1) {
                    System.out.println("Attesa di risposta...");

                    // creo un nuovo buffer per parsare la risposta
                    var buffer = new byte[4];
                    var responseDatagram = new DatagramPacket(buffer, buffer.length);
                    socket.receive(responseDatagram);
                    var value = byteArrayToInt(buffer, 0); //converto da byte array ad int
                    System.out.printf("Ricevuto: %d\n", value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static int byteArrayToInt(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xFF) << 24)
            | ((bytes[offset + 1] & 0xFF) << 16)
            | ((bytes[offset + 2] & 0xFF) << 8)
            | (bytes[offset + 3] & 0xFF);
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