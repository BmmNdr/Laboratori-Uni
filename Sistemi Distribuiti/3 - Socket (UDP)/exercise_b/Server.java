import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) {
        final var port = 8080;

        System.out.println("Server in ascolto...");
        try (var serverSocket = new DatagramSocket(port)) {
            while (true) {
                var buffer = new byte[5]; // 1 byte il comando + 4 byte l'indice del contatore
                var datagram = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(datagram);
                System.out.printf("  Datagramma ricevuto da %d\n", datagram.getPort());

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