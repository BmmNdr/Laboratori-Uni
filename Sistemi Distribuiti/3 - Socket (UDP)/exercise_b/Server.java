import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) {
        final var port = 8080;
        final Map<Integer, Integer> map = new HashMap<>();

        System.out.println("Server in ascolto...");
        try (var serverSocket = new DatagramSocket(port)) {
            while (true) {
                var buffer = new byte[5]; // 1 byte il comando + 4 byte l'indice del contatore
                var datagram = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(datagram);

                var clientPort = datagram.getPort();
                var clientIP = datagram.getAddress();

                System.out.printf("  Datagramma ricevuto da %d\n", clientPort);

                var index = byteArrayToInt(buffer, 1);

                var counter = map.getOrDefault(index, 0);

                switch (buffer[0]) {
                    case 0x01: //lettura
                        var counterBuff = intToByteArray(counter);
                        var responseDatagram = new DatagramPacket(counterBuff, counterBuff.length, clientIP, clientPort);
                        serverSocket.send(responseDatagram);
                        System.out.printf("Datagram Inviato con valore: %s\n", counter.toString());
                        break;
                    case 0x02: //incremento
                        map.put(index, ++counter);
                        break;
                    default:
                        System.out.println("Errore");
                        break;
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