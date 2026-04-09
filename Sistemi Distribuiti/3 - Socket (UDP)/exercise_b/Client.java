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
                System.out.print("Inserisci comando (1: Leggi contatore, 2: Incrementa contatore, 0: Esci): ");
                var command = scanner.nextInt();

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